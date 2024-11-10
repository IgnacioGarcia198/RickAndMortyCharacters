package com.ignacio.rickandmorty.data.models

data class RMCharacters(
    val characters: List<RMCharacter>,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
)
