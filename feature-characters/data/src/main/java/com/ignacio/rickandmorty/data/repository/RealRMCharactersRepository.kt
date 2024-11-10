package com.ignacio.rickandmorty.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.ignacio.rickandmorty.data.datasources.local.CharactersLocalDataSource
import com.ignacio.rickandmorty.data.datasources.remote.RickAndMortyApi
import com.ignacio.rickandmorty.data.mapping.toDomain
import com.ignacio.rickandmorty.data.paging.CharactersPagerFactory
import com.ignacio.rickandmorty.domain.models.RMCharacter
import com.ignacio.rickandmorty.domain.repository.RMCharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RealRMCharactersRepository @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    private val charactersLocalDataSource: CharactersLocalDataSource,
    private val pagerFactory: CharactersPagerFactory,
) : RMCharactersRepository {
    override fun getRMCharacters(query: String): Flow<PagingData<RMCharacter>> {
        return pagerFactory.create(query).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }
}