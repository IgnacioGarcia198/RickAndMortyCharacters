package com.ignacio.rickandmorty.characters.domain.repository

import com.ignacio.rickandmorty.characters.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.characters.domain.models.RMCharacter
import com.ignacio.rickandmorty.kotlin_utils.paging.PagedData
import kotlinx.coroutines.flow.Flow

interface RMCharactersPagingRepository {
    fun getRMCharacters(query: CharacterListQueryCriteria): Flow<PagedData<RMCharacter>>
}