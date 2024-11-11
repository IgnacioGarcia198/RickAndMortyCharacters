package com.ignacio.rickandmorty.presentation.character.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ignacio.rickandmorty.domain.usecases.GetRMCharacterDetail
import com.ignacio.rickandmorty.domain.usecases.GetRMCharacters
import com.ignacio.rickandmorty.presentation.character.mapping.toUi
import com.ignacio.rickandmorty.presentation.character.models.RMCharacterDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RMCharacterDetailViewModel @Inject constructor(
    private val getRMCharacterDetail: GetRMCharacterDetail,
) : ViewModel(), RMCharacterDetailViewModelContract {
    override var state by mutableStateOf<RMCharacterDetailState>(RMCharacterDetailState.Loading)

    override fun initialize(id: Int) {
        getRMCharacterDetail(id)
            .onEach { result ->
                result.onFailure {
                    state = RMCharacterDetailState.Error(it)
                }.onSuccess {
                    state = RMCharacterDetailState.Data(it.toUi())
                }
            }.launchIn(viewModelScope)
    }
}