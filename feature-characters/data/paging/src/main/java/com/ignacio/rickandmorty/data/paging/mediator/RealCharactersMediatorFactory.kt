package com.ignacio.rickandmorty.data.paging.mediator

import androidx.paging.ExperimentalPagingApi
import com.ignacio.rickandmorty.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.paging.mediator.MediatorFactory
import javax.inject.Inject

class RealCharactersMediatorFactory @Inject constructor() : MediatorFactory<Int, LocalRMCharacter> {
    @ExperimentalPagingApi
    override fun create(
        updateFromRemote: suspend (page: Int, shouldClearLocalCache: Boolean) -> Result<Boolean>,
    ): RMCharactersMediator =
        RMCharactersMediator(
            updateFromRemote,
        )
}