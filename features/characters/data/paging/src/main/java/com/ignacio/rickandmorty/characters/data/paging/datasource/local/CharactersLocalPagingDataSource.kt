package com.ignacio.rickandmorty.characters.data.paging.datasource.local

import androidx.paging.PagingSource
import com.ignacio.rickandmorty.characters.data.models.RMCharacter
import com.ignacio.rickandmorty.characters.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.characters.data.paging.models.LocalRMCharacter

interface CharactersLocalPagingDataSource {
    suspend fun upsertAll(
        characters: List<RMCharacter>,
        clear: Boolean = false,
        query: CharacterQueryCriteria = CharacterQueryCriteria.default
    )

    fun getRMCharacters(query: CharacterQueryCriteria): PagingSource<Int, LocalRMCharacter>
}
