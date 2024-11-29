package com.ignacio.rickandmorty.data.paging.mapping

import com.ignacio.rickandmorty.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.domain.models.RMCharacter

fun CharacterListQueryCriteria.toData(): CharacterQueryCriteria = CharacterQueryCriteria(
    name = name,
    status = CharacterQueryCriteria.Status.valueOf(status.name),
    gender = CharacterQueryCriteria.Gender.valueOf(gender.name),
    type = type,
    species = species
)

fun LocalRMCharacter.toDomain(): RMCharacter =
    RMCharacter(created, episode, gender, id, image, name, species, status, type, url)