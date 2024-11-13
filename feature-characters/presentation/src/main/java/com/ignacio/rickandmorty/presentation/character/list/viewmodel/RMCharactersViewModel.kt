package com.ignacio.rickandmorty.presentation.character.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.domain.usecases.GetRMCharacters
import com.ignacio.rickandmorty.paging.mapping.toAndroid
import com.ignacio.rickandmorty.presentation.character.mapping.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RMCharactersViewModel @Inject constructor(
    private val getRMCharacters: GetRMCharacters,
) : ViewModel(), RMCharactersViewModelContract {
    private val queryMF = MutableStateFlow(CharacterListQueryCriteria.default)
    override val query = queryMF.asStateFlow()

    override val pagingDataFlow = queryMF
        .flatMapLatest { getRMCharacters(query = it) }
        .map { it.toAndroid() }
        .map { data -> data.map { it.toUi() } }
        .cachedIn(viewModelScope)

    override fun justNameQuery(name: String) {
        queryMF.value = CharacterListQueryCriteria.default.copy(name = name)
    }

    override fun setQuery(queryCriteria: CharacterListQueryCriteria) {
        queryMF.value = queryCriteria
    }

    override fun clearQuery() {
        queryMF.value = CharacterListQueryCriteria.default
    }
}
