package com.ignacio.rickandmorty.characters.data.paging.datasource.remote

import com.ignacio.rickandmorty.characters.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.characters.data.paging.models.RMCharacters

interface RickAndMortyApi {
    suspend fun getCharacters(page: Int, query: CharacterQueryCriteria): Result<RMCharacters>
}