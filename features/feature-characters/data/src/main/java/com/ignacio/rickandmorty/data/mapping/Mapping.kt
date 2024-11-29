package com.ignacio.rickandmorty.data.mapping

import com.ignacio.rickandmorty.data.models.RMCharacter
import com.ignacio.rickandmorty.domain.models.RMCharacter as DomainCharacter

fun RMCharacter.toDomain(): DomainCharacter = DomainCharacter(
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
