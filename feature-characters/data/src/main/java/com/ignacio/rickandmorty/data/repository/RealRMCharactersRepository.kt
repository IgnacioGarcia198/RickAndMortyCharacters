package com.ignacio.rickandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ignacio.rickandmorty.data.datasources.local.CharactersLocalDataSource
import com.ignacio.rickandmorty.data.datasources.remote.RickAndMortyApi
import com.ignacio.rickandmorty.data.mapping.toDomain
import com.ignacio.rickandmorty.data.mediator.RMCharactersMediator
import com.ignacio.rickandmorty.domain.models.RMCharacter
import com.ignacio.rickandmorty.domain.repository.RMCharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RealRMCharactersRepository @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    private val charactersLocalDataSource: CharactersLocalDataSource,
): RMCharactersRepository {
    override fun getRMCharacters(query: String): Flow<PagingData<RMCharacter>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = RMCharactersMediator(
                localDataSource = charactersLocalDataSource,
                networkService = rickAndMortyApi,
                query = query,
            ),
            pagingSourceFactory = { charactersLocalDataSource.getRMCharacters(query) }
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }
}