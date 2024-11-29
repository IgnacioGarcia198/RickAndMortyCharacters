package com.ignacio.rickandmorty.characters.domain.repository

import com.ignacio.rickandmorty.characters.domain.models.RMCharacter
import kotlinx.coroutines.flow.Flow

interface RMCharactersRepository {
    fun getRMCharacterById(id: Int): Flow<Result<RMCharacter?>>
}