package com.ignacio.rickandmorty.framework.local.datasource

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.ignacio.rickandmorty.data.datasources.local.CharactersLocalDataSource
import com.ignacio.rickandmorty.data.models.LocalRMCharacter
import com.ignacio.rickandmorty.data.models.RMCharacter
import com.ignacio.rickandmorty.framework.local.db.AppDatabase
import com.ignacio.rickandmorty.framework.local.mapping.toDb

class RealCharactersLocalDataSource(
    private val database: AppDatabase
) : CharactersLocalDataSource {
    private val rmCharacterDao get() = database.rmCharacterDao()
    override suspend fun upsertAll(characters: List<RMCharacter>, clear: Boolean, query: String) {
        database.withTransaction {
            if (clear) {
                rmCharacterDao.deleteByQuery(query)
            }
            rmCharacterDao.upsertAll(characters.map { it.toDb() })
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getRMCharacters(query: String): PagingSource<Int, LocalRMCharacter> {
        return (if (query.isEmpty())
            rmCharacterDao.getAllRMCharacters()
        else rmCharacterDao.getRMCharacters(query)) as PagingSource<Int, LocalRMCharacter>
    }
}