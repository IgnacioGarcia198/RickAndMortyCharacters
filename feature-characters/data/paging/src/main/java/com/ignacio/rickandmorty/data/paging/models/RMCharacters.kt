package com.ignacio.rickandmorty.data.paging.models

import com.ignacio.rickandmorty.data.models.RMCharacter

data class RMCharacters(
    val characters: List<RMCharacter>,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
)
