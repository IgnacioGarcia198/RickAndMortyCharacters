package com.ignacio.rickandmorty.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.ignacio.rickandmorty.data.datasources.local.CharactersLocalDataSource
import com.ignacio.rickandmorty.data.datasources.remote.RickAndMortyApi
import com.ignacio.rickandmorty.data.mapping.toData
import com.ignacio.rickandmorty.data.mapping.toDomain
import com.ignacio.rickandmorty.data.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.data.models.LocalRMCharacter
import com.ignacio.rickandmorty.data.paging.CharactersPagerFactory
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.domain.repository.RMCharactersRepository
import com.ignacio.rickandmorty.kotlin_utils.extensions.mapResultFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.ignacio.rickandmorty.domain.models.RMCharacter as DomainCharacter

class RealRMCharactersRepository @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    private val charactersLocalDataSource: CharactersLocalDataSource,
    private val pagerFactory: CharactersPagerFactory<Int, LocalRMCharacter>,
) : RMCharactersRepository {
    override fun getRMCharacters(query: CharacterListQueryCriteria): Flow<PagingData<DomainCharacter>> {
        val dataQuery = query.toData()
        return pagerFactory.create(
            updateFromRemote = { page, shouldClearLocalCache ->
                updateFromRemote(
                    query = query.toData(),
                    page = page,
                    shouldClearLocalCache = shouldClearLocalCache,
                )
            }
        ) { charactersLocalDataSource.getRMCharacters(dataQuery) }.flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    suspend fun updateFromRemote(
        query: CharacterQueryCriteria,
        page: Int,
        shouldClearLocalCache: Boolean,
    ): Result<Boolean> {
        // Suspending network load via Ktor. This doesn't need to be
        // wrapped in a withContext(Dispatcher.IO) { ... } block since
        // Ktor does it automatically.
        return rickAndMortyApi.getCharacters(page = page, query = query)
            .mapCatching { response ->
                charactersLocalDataSource.upsertAll(
                    response.characters,
                    clear = shouldClearLocalCache,
                    query = query
                )
                !response.hasNextPage
            }
    }

    override fun getRMCharacterById(id: Int): Flow<Result<DomainCharacter?>> {
        return charactersLocalDataSource.getRMCharacterById(id)
            .mapResultFlow { it?.toDomain() }
    }
}