package com.ignacio.rickandmorty.characters.presentation.mapping

import com.ignacio.rickandmorty.characters.domain.models.RMCharacter
import com.ignacio.rickandmorty.characters.presentation.models.UiRMCharacter

fun RMCharacter.toUi(): UiRMCharacter = UiRMCharacter(
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