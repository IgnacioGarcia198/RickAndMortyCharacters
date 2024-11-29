package com.ignacio.rickandmorty.characters.domain.usecases

import com.ignacio.rickandmorty.characters.domain.models.RMCharacter
import com.ignacio.rickandmorty.characters.domain.repository.RMCharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRMCharacterDetail @Inject constructor(
    private val repository: RMCharactersRepository,
) {
    operator fun invoke(id: Int): Flow<Result<RMCharacter?>> {
        return repository.getRMCharacterById(id)
    }
}