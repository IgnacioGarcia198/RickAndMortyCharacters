package com.ignacio.rickandmorty.domain.usecases

import androidx.paging.PagingData
import com.ignacio.rickandmorty.domain.models.RMCharacter
import com.ignacio.rickandmorty.domain.repository.RMCharactersRepository
import kotlinx.coroutines.flow.Flow

class GetRMCharacters(
    private val repository: RMCharactersRepository
) {
    operator fun invoke(query: String): Flow<PagingData<RMCharacter>> {
        return repository.getRMCharacters(query)
    }
}