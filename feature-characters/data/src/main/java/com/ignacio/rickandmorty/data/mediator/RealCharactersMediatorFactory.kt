package com.ignacio.rickandmorty.data.mediator

import com.ignacio.rickandmorty.data.datasources.local.CharactersLocalDataSource
import com.ignacio.rickandmorty.data.datasources.remote.RickAndMortyApi
import javax.inject.Inject

class RealCharactersMediatorFactory @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    private val charactersLocalDataSource: CharactersLocalDataSource,
): CharactersMediatorFactory {
    override fun create(query: String): RMCharactersMediator = RMCharactersMediator(
        query = query,
        localDataSource = charactersLocalDataSource,
        networkService = rickAndMortyApi,
    )
}