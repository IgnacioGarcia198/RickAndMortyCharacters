package com.ignacio.rickandmorty.domain.usecases

import com.ignacio.rickandmorty.domain.models.RMCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetRMCharacterDetail @Inject constructor() {
    operator fun invoke(id: Int): Flow<Result<RMCharacter?>> {
        return flowOf(Result.success(RMCharacter.dummy))
    }
}