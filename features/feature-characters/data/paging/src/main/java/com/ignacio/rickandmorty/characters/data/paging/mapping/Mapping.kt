package com.ignacio.rickandmorty.characters.data.paging.mapping

import com.ignacio.rickandmorty.characters.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.characters.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.characters.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.characters.domain.models.RMCharacter

fun CharacterListQueryCriteria.toData(): CharacterQueryCriteria = CharacterQueryCriteria(
    name = name,
    status = CharacterQueryCriteria.Status.valueOf(status.name),
    gender = CharacterQueryCriteria.Gender.valueOf(gender.name),
    type = type,
    species = species
)

fun LocalRMCharacter.toDomain(): RMCharacter =
    RMCharacter(created, episode, gender, id, image, name, species, status, type, url)