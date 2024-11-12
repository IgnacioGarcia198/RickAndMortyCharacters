package com.ignacio.rickandmorty.data.mediator

import com.ignacio.rickandmorty.data.models.CharacterQueryCriteria

interface CharactersMediatorFactory {
    fun create(query: CharacterQueryCriteria): RMCharactersMediator
}