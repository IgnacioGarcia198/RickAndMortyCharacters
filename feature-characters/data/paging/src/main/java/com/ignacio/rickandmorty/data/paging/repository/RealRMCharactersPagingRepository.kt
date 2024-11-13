package com.ignacio.rickandmorty.data.paging.repository

import com.ignacio.rickandmorty.data.paging.datasource.local.CharactersLocalPagingDataSource
import com.ignacio.rickandmorty.data.paging.datasource.remote.RickAndMortyApi
import com.ignacio.rickandmorty.data.paging.mapping.toData
import com.ignacio.rickandmorty.data.paging.mapping.toDomain
import com.ignacio.rickandmorty.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.domain.repository.RMCharactersPagingRepository
import com.ignacio.rickandmorty.kotlin_utils.extensions.mapPagedFlow
import com.ignacio.rickandmorty.kotlin_utils.paging.PagedData
import com.ignacio.rickandmorty.paging.pager.PagerFactory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.ignacio.rickandmorty.domain.models.RMCharacter as DomainCharacter

class RealRMCharactersPagingRepository @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    private val charactersLocalDataSource: CharactersLocalPagingDataSource,
    private val pagerFactory: PagerFactory<Int, LocalRMCharacter>,
) : RMCharactersPagingRepository {
    override fun getRMCharacters(query: CharacterListQueryCriteria): Flow<PagedData<DomainCharacter>> {
        val dataQuery = query.toData()
        return pagerFactory.create(
            updateFromRemote = { page, shouldClearLocalCache ->
                updateFromRemote(
                    query = dataQuery,
                    page = page,
                    shouldClearLocalCache = shouldClearLocalCache,
                )
            }
        ) { charactersLocalDataSource.getRMCharacters(dataQuery) }
            .flow
            .mapPagedFlow { it.toDomain() }
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
}