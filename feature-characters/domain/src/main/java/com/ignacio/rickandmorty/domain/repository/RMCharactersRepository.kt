package com.ignacio.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.domain.models.RMCharacter
import kotlinx.coroutines.flow.Flow

interface RMCharactersRepository {
    fun getRMCharacters(query: CharacterListQueryCriteria): Flow<PagingData<RMCharacter>>
    fun getRMCharacterById(id: Int): Flow<Result<RMCharacter?>>
}