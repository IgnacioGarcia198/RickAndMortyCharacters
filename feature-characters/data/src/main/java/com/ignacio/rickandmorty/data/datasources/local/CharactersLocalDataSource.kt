package com.ignacio.rickandmorty.data.datasources.local

import androidx.paging.PagingSource
import com.ignacio.rickandmorty.data.models.LocalRMCharacter
import com.ignacio.rickandmorty.data.models.RMCharacter
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria
import kotlinx.coroutines.flow.Flow

interface CharactersLocalDataSource {
    suspend fun upsertAll(characters: List<RMCharacter>, clear: Boolean = false, query: CharacterListQueryCriteria = CharacterListQueryCriteria.default)
    fun getRMCharacters(query: CharacterListQueryCriteria): PagingSource<Int, LocalRMCharacter>
    fun getRMCharacterById(id: Int): Flow<Result<RMCharacter?>>
}
