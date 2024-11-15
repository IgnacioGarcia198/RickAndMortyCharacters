package com.ignacio.rickandmorty.framework.remote.mapping

import com.ignacio.rickandmorty.data.models.RMCharacter
import com.ignacio.rickandmorty.data.paging.models.RMCharacters
import com.ignacio.rickandmorty.framework.remote.models.RMCharactersResponse

fun RMCharactersResponse.toRMCharacters(): RMCharacters {
    return when (this) {
        is RMCharactersResponse.Error -> RMCharacters.NoResults
        is RMCharactersResponse.Success -> RMCharacters.Characters(
            characters = characters.map { it.toData() },
            hasNextPage = info.next != null,
            hasPreviousPage = info.prev != null,
        )
    }
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
