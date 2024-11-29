package com.ignacio.rickandmorty.domain.usecases

import com.ignacio.rickandmorty.domain.models.RMCharacter
import com.ignacio.rickandmorty.domain.repository.RMCharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRMCharacterDetail @Inject constructor(
    private val repository: RMCharactersRepository,
) {
    operator fun invoke(id: Int): Flow<Result<RMCharacter?>> {
        return repository.getRMCharacterById(id)
    }
}