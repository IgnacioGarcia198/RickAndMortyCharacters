package com.ignacio.rickandmorty.characters.domain.usecases

import com.ignacio.rickandmorty.characters.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.characters.domain.models.RMCharacter
import com.ignacio.rickandmorty.characters.domain.repository.RMCharactersPagingRepository
import com.ignacio.rickandmorty.kotlin_utils.paging.PagedData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRMCharacters @Inject constructor(
    private val repository: RMCharactersPagingRepository
) {
    operator fun invoke(query: CharacterListQueryCriteria): Flow<PagedData<RMCharacter>> {
        return repository.getRMCharacters(query)
    }
}