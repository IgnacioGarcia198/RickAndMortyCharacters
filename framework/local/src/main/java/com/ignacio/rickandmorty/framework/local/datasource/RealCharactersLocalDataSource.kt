package com.ignacio.rickandmorty.framework.local.datasource

import androidx.paging.PagingSource
import androidx.room.withTransaction
import androidx.sqlite.db.SimpleSQLiteQuery
import com.ignacio.rickandmorty.characters.data.datasources.local.CharactersLocalDataSource
import com.ignacio.rickandmorty.characters.data.models.RMCharacter
import com.ignacio.rickandmorty.framework.local.db.AppDatabase
import com.ignacio.rickandmorty.framework.local.mapping.toData
import com.ignacio.rickandmorty.framework.local.mapping.toDb
import com.ignacio.rickandmorty.framework.local.models.DbRMCharacter
import com.ignacio.rickandmorty.kotlin_utils.extensions.asResultFlow
import com.ignacio.rickandmorty.characters.data.paging.datasource.local.CharactersLocalPagingDataSource
import com.ignacio.rickandmorty.characters.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.characters.data.paging.models.LocalRMCharacter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RealCharactersLocalDataSource @Inject constructor(
    private val database: AppDatabase
) : CharactersLocalDataSource, CharactersLocalPagingDataSource {
    private val rmCharacterDao get() = database.rmCharacterDao()
    override suspend fun upsertAll(
        characters: List<RMCharacter>,
        clear: Boolean,
        query: CharacterQueryCriteria
    ) {
        database.withTransaction {
            if (clear) {
                rmCharacterDao.deleteByQuery(query.toQuery("DELETE FROM ${DbRMCharacter.TABLE_NAME}"))
            }
            rmCharacterDao.upsertAll(characters.map { it.toDb() })
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getRMCharacters(query: CharacterQueryCriteria): PagingSource<Int, LocalRMCharacter> {
        val sql = query.toQuery("SELECT * FROM ${DbRMCharacter.TABLE_NAME}")
        return rmCharacterDao.getRMCharacters(sql) as PagingSource<Int, LocalRMCharacter>
    }

    private fun CharacterQueryCriteria.toQuery(queryStart: String): SimpleSQLiteQuery {
        val sql = StringBuilder(queryStart)
        val args = mutableListOf<String>()
        if (name.isNotEmpty()) {
            sql.appendQuerySegment("${DbRMCharacter.NAME_COLUMN} LIKE ?")
            args.add("%$name%")
        }
        if (type.isNotEmpty()) {
            sql.appendQuerySegment("${DbRMCharacter.TYPE_COLUMN} LIKE ?")
            args.add("%$type%")
        }
        if (species.isNotEmpty()) {
            sql.appendQuerySegment("${DbRMCharacter.SPECIES_COLUMN} LIKE ?")
            args.add("%$species%")
        }
        if (status != CharacterQueryCriteria.Status.ANY) {
            sql.appendQuerySegment("${DbRMCharacter.STATUS_COLUMN} LIKE ?")
            args.add("%${status.name}%")
        }
        if (gender != CharacterQueryCriteria.Gender.ANY) {
            sql.appendQuerySegment("${DbRMCharacter.GENDER_COLUMN} LIKE ?")
            args.add("%${gender.name}%")
        }
        return SimpleSQLiteQuery(sql.toString(), args.toTypedArray())
    }

    private fun StringBuilder.appendQuerySegment(segment: String) {
        if (endsWith(DbRMCharacter.TABLE_NAME)) append(" WHERE $segment")
        else append(" AND $segment")
    }


    override fun getRMCharacterById(id: Int): Flow<Result<RMCharacter?>> {
        return rmCharacterDao.getRMCharacterById(id).asResultFlow { it?.toData() }
    }
}
