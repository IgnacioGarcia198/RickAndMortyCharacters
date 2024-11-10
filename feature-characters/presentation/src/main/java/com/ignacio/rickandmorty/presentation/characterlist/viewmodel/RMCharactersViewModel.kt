package com.ignacio.rickandmorty.presentation.characterlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ignacio.rickandmorty.domain.usecases.GetRMCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RMCharactersViewModel @Inject constructor(
    private val getRMCharacters: GetRMCharacters,
): ViewModel(), RMCharactersViewModelContract {
    private val queryMF = MutableStateFlow("")

    override val pagingDataFlow = queryMF
        .flatMapLatest { getRMCharacters(query = it) }
        .cachedIn(viewModelScope)
}