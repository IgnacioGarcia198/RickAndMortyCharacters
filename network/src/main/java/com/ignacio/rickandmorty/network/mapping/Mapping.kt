package com.ignacio.rickandmorty.network.mapping

import com.ignacio.rickandmorty.data.models.RMCharacter
import com.ignacio.rickandmorty.data.models.RMCharacters
import com.ignacio.rickandmorty.network.models.RMCharactersResponse

fun RMCharactersResponse.getRMCharacters(): List<RMCharacter> {
    return this.characters.map { it.toData() }
}

fun RMCharactersResponse.toRMCharacters(): RMCharacters {
    return RMCharacters(
        characters = characters.map { it.toData() },
        hasNextPage = info.next != null,
        hasPreviousPage = info.prev != null,
    )
}

fun RMCharactersResponse.RMCharacter.toData(): RMCharacter = RMCharacter(
    created = created,
    episode = episode,
    gender = gender,
    id = id,
    image = image,
    name = name,
    species = species,
    status = status,
    type = type,
    url = url
)