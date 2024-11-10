package com.ignacio.rickandmorty.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RMCharactersResponse(
    val info: Info,
    @SerialName("results")
    val characters: List<RMCharacter>
) {
    @Serializable
    data class Info(
        val count: Int,
        val next: String? = null,
        val pages: Int,
        val prev: String? = null
    )

    @Serializable
    data class RMCharacter(
        val created: String,
        val episode: List<String>,
        val gender: String,
        val id: Int,
        val image: String? = null,
        val location: RMLinkTrait,
        val name: String,
        val origin: RMLinkTrait,
        val species: String,
        val status: String,
        val type: String? = null,
        val url: String
    )
}