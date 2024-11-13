package com.ignacio.rickandmorty.domain.repository

import com.ignacio.rickandmorty.domain.models.RMCharacter
import kotlinx.coroutines.flow.Flow

interface RMCharactersRepository {
    fun getRMCharacterById(id: Int): Flow<Result<RMCharacter?>>
}