package com.ignacio.rickandmorty.framework.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Upsert
import androidx.sqlite.db.SupportSQLiteQuery
import com.ignacio.rickandmorty.framework.local.extensions.toSupportSQLiteQuery
import com.ignacio.rickandmorty.framework.local.models.DbRMCharacter
import com.ignacio.rickandmorty.framework.local.sql.SqlQueryBuilder
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RMCharacterDao {
    @Upsert
    abstract fun upsertAll(characters: List<DbRMCharacter>)

    fun deleteByQuery(query: SqlQueryBuilder.SqlQueryData): Int {
        return deleteByQuery(query.toSupportSQLiteQuery())
    }

    @RawQuery
    abstract fun deleteByQuery(query: SupportSQLiteQuery): Int

    @Query("DELETE FROM rickAndMortyCharacters")
    abstract fun clearAll()

    fun getRMCharacters(query: SqlQueryBuilder.SqlQueryData): PagingSource<Int, DbRMCharacter> {
        return getRMCharacters(query.toSupportSQLiteQuery())
    }

    @RawQuery(observedEntities = [DbRMCharacter::class])
    abstract fun getRMCharacters(query: SupportSQLiteQuery): PagingSource<Int, DbRMCharacter>

    @Query("SELECT * FROM rickAndMortyCharacters WHERE id = :id")
    abstract fun getRMCharacterById(id: Int): Flow<DbRMCharacter?>
}