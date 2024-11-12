package com.ignacio.rickandmorty.presentation.character.list.viewmodel

import androidx.paging.PagingData
import com.ignacio.rickandmorty.presentation.character.models.UiRMCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface RMCharactersViewModelContract {
    val pagingDataFlow: Flow<PagingData<UiRMCharacter>>

    fun setQuery(query: String)
}