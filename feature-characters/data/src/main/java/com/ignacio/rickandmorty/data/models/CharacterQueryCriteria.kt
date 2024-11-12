package com.ignacio.rickandmorty.data.models

data class CharacterQueryCriteria(
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: Gender,
) {
    enum class Status {
        ALIVE, DEAD, UNKNOWN, ANY;

        override fun toString(): String = name.lowercase()
    }

    enum class Gender {
        FEMALE, MALE, GENDERLESS, UNKNOWN, ANY;

        override fun toString(): String = name.lowercase()
    }
    companion object {
        val default = CharacterQueryCriteria(
            name = "",
            status = Status.ANY,
            species = "",
            type = "",
            gender = Gender.ANY,
        )
    }
}
