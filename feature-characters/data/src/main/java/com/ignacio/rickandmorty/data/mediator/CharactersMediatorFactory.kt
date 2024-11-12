package com.ignacio.rickandmorty.data.mediator

import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria

interface CharactersMediatorFactory {
    fun create(query: CharacterListQueryCriteria): RMCharactersMediator
}