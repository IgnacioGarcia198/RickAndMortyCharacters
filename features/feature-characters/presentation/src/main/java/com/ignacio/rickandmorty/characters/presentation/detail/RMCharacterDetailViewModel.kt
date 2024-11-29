package com.ignacio.rickandmorty.characters.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacio.rickandmorty.characters.domain.usecases.GetRMCharacterDetail
import com.ignacio.rickandmorty.characters.presentation.mapping.toUi
import com.ignacio.rickandmorty.characters.presentation.models.RMCharacterDetailState
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
                }.onSuccess { character ->
                    state = if (character != null) {
                        RMCharacterDetailState.Data(character.toUi())
                    } else {
                        RMCharacterDetailState.CharacterNotFound
                    }
                }
            }.launchIn(viewModelScope)
    }

    @AssistedFactory
    interface ViewModelFactory {
        fun create(id: Int): RMCharacterDetailViewModel
    }
}