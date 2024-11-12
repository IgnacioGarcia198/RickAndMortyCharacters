package com.ignacio.rickandmorty.data.datasources.local

import androidx.paging.PagingSource
import com.ignacio.rickandmorty.data.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.data.models.LocalRMCharacter
import com.ignacio.rickandmorty.data.models.RMCharacter
import kotlinx.coroutines.flow.Flow

interface CharactersLocalDataSource {
    suspend fun upsertAll(
        characters: List<RMCharacter>,
        clear: Boolean = false,
        query: CharacterQueryCriteria = CharacterQueryCriteria.default
    )

    fun getRMCharacters(query: CharacterQueryCriteria): PagingSource<Int, LocalRMCharacter>
    fun getRMCharacterById(id: Int): Flow<Result<RMCharacter?>>
}
