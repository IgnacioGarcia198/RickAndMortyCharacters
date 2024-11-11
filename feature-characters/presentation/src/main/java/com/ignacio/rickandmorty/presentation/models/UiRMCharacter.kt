package com.ignacio.rickandmorty.presentation.models

data class UiRMCharacter(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String?,
    val name: String,
    val species: String,
    val status: String,
    val type: String?,
    val url: String,
)
