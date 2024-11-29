package com.ignacio.rickandmorty.data.paging.models

data class TestLocalRMCharacter(
    override val created: String,
    override val episode: List<String>,
    override val gender: String,
    override val id: Int,
    override val image: String?,
    override val name: String,
    override val species: String,
    override val status: String,
    override val type: String?,
    override val url: String,
) : LocalRMCharacter {
    companion object {
        val dummy = TestLocalRMCharacter(
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