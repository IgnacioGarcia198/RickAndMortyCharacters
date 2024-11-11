package com.ignacio.rickandmorty.presentation.characterlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ignacio.rickandmorty.domain.usecases.GetRMCharacters
import com.ignacio.rickandmorty.presentation.mapping.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RMCharactersViewModel @Inject constructor(
    private val getRMCharacters: GetRMCharacters,
): ViewModel(), RMCharactersViewModelContract {
    private val queryMF = MutableStateFlow("")

    override val pagingDataFlow = queryMF
        .flatMapLatest { getRMCharacters(query = it) }
        .map { data -> data.map { it.toUi() } }
        .cachedIn(viewModelScope)
}