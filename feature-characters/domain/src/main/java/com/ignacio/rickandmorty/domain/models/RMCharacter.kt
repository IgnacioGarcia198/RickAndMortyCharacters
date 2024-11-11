package com.ignacio.rickandmorty.domain.models

data class RMCharacter(
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
) {
    companion object {
        val dummy = RMCharacter(
            created = "created",
            episode = emptyList(),
            gender = "male",
            id = 1,
            image = null,
            name = "name",
            species = "species",
            status = "status",
            type = "type",
            url = "url"
        )
    }
}
