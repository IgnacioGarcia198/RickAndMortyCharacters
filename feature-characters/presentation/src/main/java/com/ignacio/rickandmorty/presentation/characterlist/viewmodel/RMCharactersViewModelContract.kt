package com.ignacio.rickandmorty.presentation.characterlist.viewmodel

import androidx.paging.PagingData
import com.ignacio.rickandmorty.domain.models.RMCharacter
import kotlinx.coroutines.flow.Flow

interface RMCharactersViewModelContract {
    val pagingDataFlow: Flow<PagingData<RMCharacter>>
}