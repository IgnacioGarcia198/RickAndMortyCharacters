package com.ignacio.rickandmorty.characters.presentation.models

sealed interface RMCharacterDetailState {
    data object Loading : RMCharacterDetailState
    data class Error(val error: Throwable?) : RMCharacterDetailState
    data class Data(val character: UiRMCharacter) : RMCharacterDetailState
    data object CharacterNotFound : RMCharacterDetailState
}
