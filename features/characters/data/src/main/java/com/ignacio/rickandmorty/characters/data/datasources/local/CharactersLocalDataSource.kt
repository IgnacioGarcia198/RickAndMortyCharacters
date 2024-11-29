package com.ignacio.rickandmorty.characters.data.datasources.local

import com.ignacio.rickandmorty.characters.data.models.RMCharacter
import kotlinx.coroutines.flow.Flow

interface CharactersLocalDataSource {
    fun getRMCharacterById(id: Int): Flow<Result<RMCharacter?>>
}
