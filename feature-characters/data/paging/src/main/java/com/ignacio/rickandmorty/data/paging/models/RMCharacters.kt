package com.ignacio.rickandmorty.data.paging.models

import com.ignacio.rickandmorty.data.models.RMCharacter

sealed interface RMCharacters {
    data class Characters(
        val characters: List<RMCharacter>,
        val hasNextPage: Boolean,
        val hasPreviousPage: Boolean,
    ): RMCharacters
    data object NoResults: RMCharacters
}
