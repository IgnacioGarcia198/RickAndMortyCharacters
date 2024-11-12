package com.ignacio.rickandmorty.domain.models

data class CharacterListQueryCriteria(
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: Gender,
    val justName: Boolean = false,
) {
    enum class Status {
        alive, dead, unknown, any
    }

    enum class Gender {
        female, male, genderless, unknown, any
    }
    companion object {
        val default = CharacterListQueryCriteria(
            name = "",
            status = Status.any,
            species = "",
            type = "",
            gender = Gender.any,
        )
    }
}
