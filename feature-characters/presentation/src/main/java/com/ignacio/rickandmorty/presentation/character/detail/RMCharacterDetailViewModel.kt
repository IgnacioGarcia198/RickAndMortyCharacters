package com.ignacio.rickandmorty.presentation.character.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacio.rickandmorty.domain.usecases.GetRMCharacterDetail
import com.ignacio.rickandmorty.presentation.character.mapping.toUi
import com.ignacio.rickandmorty.presentation.character.models.RMCharacterDetailState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel(assistedFactory = RMCharacterDetailViewModel.ViewModelFactory::class)
class RMCharacterDetailViewModel @AssistedInject constructor(
    private val getRMCharacterDetail: GetRMCharacterDetail,
    @Assisted val id: Int,
) : ViewModel(), RMCharacterDetailViewModelContract {
    override var state by mutableStateOf<RMCharacterDetailState>(RMCharacterDetailState.Loading)

    init {
        initialize(id)
    }

    private fun initialize(id: Int) {
        getRMCharacterDetail(id)
            .onEach { result ->
                result.onFailure {
                    state = RMCharacterDetailState.Error(it)
                }.onSuccess {
                    state = RMCharacterDetailState.Data(it.toUi())
                }
            }.launchIn(viewModelScope)
    }

    @AssistedFactory
    interface ViewModelFactory {
        fun create(id: Int): RMCharacterDetailViewModel
    }
}