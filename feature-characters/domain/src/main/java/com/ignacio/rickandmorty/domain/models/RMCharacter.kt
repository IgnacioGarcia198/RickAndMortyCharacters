package com.ignacio.rickandmorty.domain.models

data class RMCharacter(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String? = null,
    val name: String,
    val species: String,
    val status: String,
    val type: String? = null,
    val url: String,
)
