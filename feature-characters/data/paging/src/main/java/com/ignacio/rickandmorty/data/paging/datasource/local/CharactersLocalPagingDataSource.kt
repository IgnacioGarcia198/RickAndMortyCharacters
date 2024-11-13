package com.ignacio.rickandmorty.data.paging.datasource.local

import androidx.paging.PagingSource
import com.ignacio.rickandmorty.data.models.RMCharacter
import com.ignacio.rickandmorty.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.data.paging.models.LocalRMCharacter

interface CharactersLocalPagingDataSource {
    suspend fun upsertAll(
        characters: List<RMCharacter>,
        clear: Boolean = false,
        query: CharacterQueryCriteria = CharacterQueryCriteria.default
    )

    fun getRMCharacters(query: CharacterQueryCriteria): PagingSource<Int, LocalRMCharacter>
}
