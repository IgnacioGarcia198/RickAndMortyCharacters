package com.ignacio.rickandmorty.data.mediator

interface CharactersMediatorFactory {
    fun create(updateFromRemote: suspend (page: Int, shouldClearLocalCache: Boolean) -> Result<Boolean>): RMCharactersMediator
}