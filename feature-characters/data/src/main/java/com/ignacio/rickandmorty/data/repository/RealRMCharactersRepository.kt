package com.ignacio.rickandmorty.data.repository

import com.ignacio.rickandmorty.data.datasources.local.CharactersLocalDataSource
import com.ignacio.rickandmorty.data.mapping.toDomain
import com.ignacio.rickandmorty.domain.repository.RMCharactersRepository
import com.ignacio.rickandmorty.kotlin_utils.extensions.mapResultFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.ignacio.rickandmorty.domain.models.RMCharacter as DomainCharacter

class RealRMCharactersRepository @Inject constructor(
    private val charactersLocalDataSource: CharactersLocalDataSource,
) : RMCharactersRepository {
    override fun getRMCharacterById(id: Int): Flow<Result<DomainCharacter?>> {
        return charactersLocalDataSource.getRMCharacterById(id)
            .mapResultFlow { it?.toDomain() }
    }
}