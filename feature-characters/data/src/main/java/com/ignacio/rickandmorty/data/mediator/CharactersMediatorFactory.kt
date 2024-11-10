package com.ignacio.rickandmorty.data.mediator

interface CharactersMediatorFactory {
    fun create(query: String): RMCharactersMediator
}