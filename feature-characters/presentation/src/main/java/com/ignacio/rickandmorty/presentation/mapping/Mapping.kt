package com.ignacio.rickandmorty.presentation.mapping

import com.ignacio.rickandmorty.domain.models.RMCharacter
import com.ignacio.rickandmorty.presentation.models.UiRMCharacter

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