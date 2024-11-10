package com.ignacio.rickandmorty.data.mapping

import com.ignacio.rickandmorty.data.models.LocalRMCharacter
import com.ignacio.rickandmorty.domain.models.RMCharacter

fun LocalRMCharacter.toDomain(): RMCharacter = RMCharacter(
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