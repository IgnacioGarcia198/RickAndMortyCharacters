package com.ignacio.rickandmorty.presentation.characterlist.viewmodel

import androidx.paging.PagingData
import com.ignacio.rickandmorty.presentation.models.UiRMCharacter
import kotlinx.coroutines.flow.Flow

interface RMCharactersViewModelContract {
    val pagingDataFlow: Flow<PagingData<UiRMCharacter>>
}