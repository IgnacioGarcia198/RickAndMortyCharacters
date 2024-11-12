package com.ignacio.rickandmorty.data.mediator

import androidx.paging.LoadType
import com.ignacio.rickandmorty.data.models.LocalRMCharacter
import javax.inject.Inject

class RealCharactersMediatorFactory @Inject constructor() : CharactersMediatorFactory {
    override fun create(
        updateFromRemote: suspend (page: Int, shouldClearLocalCache: Boolean) -> Result<Boolean>,
    ): RMCharactersMediator =
        RMCharactersMediator(
            updateFromRemote,
        )
}