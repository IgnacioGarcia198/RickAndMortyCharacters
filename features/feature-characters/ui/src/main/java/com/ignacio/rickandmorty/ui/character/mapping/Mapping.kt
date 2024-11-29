package com.ignacio.rickandmorty.ui.character.mapping

import android.content.Context
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.resources.R

fun CharacterListQueryCriteria.Status.localized(context: Context): String = when(this) {
    CharacterListQueryCriteria.Status.ANY -> context.getString(R.string.any)
    CharacterListQueryCriteria.Status.ALIVE -> context.getString(R.string.status_alive)
    CharacterListQueryCriteria.Status.DEAD -> context.getString(R.string.status_dead)
    CharacterListQueryCriteria.Status.UNKNOWN -> context.getString(R.string.unknown)
}

fun CharacterListQueryCriteria.Gender.localized(context: Context): String = when(this) {
    CharacterListQueryCriteria.Gender.ANY -> context.getString(R.string.any)
    CharacterListQueryCriteria.Gender.MALE -> context.getString(R.string.gender_male)
    CharacterListQueryCriteria.Gender.FEMALE -> context.getString(R.string.gender_female)
    CharacterListQueryCriteria.Gender.GENDERLESS -> context.getString(R.string.gender_genderless)
    CharacterListQueryCriteria.Gender.UNKNOWN -> context.getString(R.string.unknown)
}
