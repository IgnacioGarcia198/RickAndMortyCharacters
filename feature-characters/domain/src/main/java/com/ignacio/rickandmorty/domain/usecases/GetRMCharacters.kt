package com.ignacio.rickandmorty.domain.usecases

import androidx.paging.PagingData
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.domain.models.RMCharacter
import com.ignacio.rickandmorty.domain.repository.RMCharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRMCharacters @Inject constructor(
    private val repository: RMCharactersRepository
) {
    operator fun invoke(query: CharacterListQueryCriteria): Flow<PagingData<RMCharacter>> {
        return repository.getRMCharacters(query)
    }
}