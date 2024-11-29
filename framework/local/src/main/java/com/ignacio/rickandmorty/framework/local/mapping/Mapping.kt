package com.ignacio.rickandmorty.framework.local.mapping

import com.ignacio.rickandmorty.characters.data.models.RMCharacter
import com.ignacio.rickandmorty.framework.local.models.DbRMCharacter

fun RMCharacter.toDb(): DbRMCharacter = DbRMCharacter(
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

fun DbRMCharacter.toData(): RMCharacter = RMCharacter(
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
