package com.ignacio.rickandmorty.data.paging.mediator

import androidx.paging.ExperimentalPagingApi
import com.ignacio.rickandmorty.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.data.paging.updater.RemoteLocalUpdater
import com.ignacio.rickandmorty.paging.mediator.MediatorFactory
import javax.inject.Inject

class RealCharactersMediatorFactory @Inject constructor(
    private val updater: RemoteLocalUpdater<CharacterQueryCriteria, Int>,
) : MediatorFactory<Int, LocalRMCharacter, CharacterQueryCriteria> {
    @ExperimentalPagingApi
    override fun create(query: CharacterQueryCriteria): RMCharactersMediator =
        RMCharactersMediator(
            updater = updater, queryCriteria = query,
        )
}