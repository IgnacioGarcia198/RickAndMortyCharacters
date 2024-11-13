package com.ignacio.rickandmorty.data.datasources.local

import com.ignacio.rickandmorty.data.models.RMCharacter
import kotlinx.coroutines.flow.Flow

interface CharactersLocalDataSource {
    fun getRMCharacterById(id: Int): Flow<Result<RMCharacter?>>
}
