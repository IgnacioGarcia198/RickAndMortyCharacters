package com.ignacio.rickandmorty.framework.local.datasource

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.ignacio.rickandmorty.characters.data.datasources.local.CharactersLocalDataSource
import com.ignacio.rickandmorty.characters.data.models.RMCharacter
import com.ignacio.rickandmorty.characters.data.paging.datasource.local.CharactersLocalPagingDataSource
import com.ignacio.rickandmorty.characters.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.characters.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.framework.local.db.AppDatabase
import com.ignacio.rickandmorty.framework.local.mapping.toData
import com.ignacio.rickandmorty.framework.local.mapping.toDb
import com.ignacio.rickandmorty.framework.local.models.DbRMCharacter
import com.ignacio.rickandmorty.framework.local.sql.SqlQueryBuilder
import com.ignacio.rickandmorty.framework.local.sql.addSegment
import com.ignacio.rickandmorty.kotlin_utils.extensions.asResultFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RealCharactersLocalDataSource @Inject constructor(
    private val database: AppDatabase,
    private val sqlQueryBuilder: SqlQueryBuilder,
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

    private fun CharacterQueryCriteria.toQuery(queryStart: String): SqlQueryBuilder.SqlQueryData {
        val segments = mutableListOf<SqlQueryBuilder.SqlQuerySegment>()
        if (name.isNotEmpty()) {
            segments.addSegment("${DbRMCharacter.NAME_COLUMN} LIKE ?", "%$name%")
        }
        if (type.isNotEmpty()) {
            segments.addSegment("${DbRMCharacter.TYPE_COLUMN} LIKE ?", "%$type%")
        }
        if (species.isNotEmpty()) {
            segments.addSegment("${DbRMCharacter.SPECIES_COLUMN} LIKE ?", "%$species%")
        }
        if (status != CharacterQueryCriteria.Status.ANY) {
            segments.addSegment("${DbRMCharacter.STATUS_COLUMN} LIKE ?", "%${status.name}%")
        }
        if (gender != CharacterQueryCriteria.Gender.ANY) {
            segments.addSegment("${DbRMCharacter.GENDER_COLUMN} LIKE ?", "%${gender.name}%")
        }
        return sqlQueryBuilder.buildWhereQuery(queryStart, segments)
    }


    override fun getRMCharacterById(id: Int): Flow<Result<RMCharacter?>> {
        return rmCharacterDao.getRMCharacterById(id).asResultFlow { it?.toData() }
    }
}
