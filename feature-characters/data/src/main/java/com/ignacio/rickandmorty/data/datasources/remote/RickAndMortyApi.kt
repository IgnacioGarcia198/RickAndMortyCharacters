package com.ignacio.rickandmorty.data.datasources.remote

import com.ignacio.rickandmorty.data.models.RMCharacters
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria

interface RickAndMortyApi {
    suspend fun getCharacters(page: Int, query: CharacterListQueryCriteria): Result<RMCharacters>
}