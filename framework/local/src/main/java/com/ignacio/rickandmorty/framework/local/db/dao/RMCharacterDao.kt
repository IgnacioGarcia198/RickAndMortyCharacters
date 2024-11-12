package com.ignacio.rickandmorty.framework.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Upsert
import androidx.sqlite.db.SupportSQLiteQuery
import com.ignacio.rickandmorty.framework.local.models.DbRMCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface RMCharacterDao {
    @Upsert
    fun upsertAll(characters: List<DbRMCharacter>)

    @Query("DELETE FROM rickAndMortyCharacters WHERE name LIKE :query")
    fun deleteByName(query: String)

    @RawQuery
    fun deleteByQuery(query: SupportSQLiteQuery): Int

    @Query("DELETE FROM rickAndMortyCharacters")
    fun clearAll()

    @Query("SELECT * FROM rickAndMortyCharacters WHERE name LIKE :query")
    fun getRMCharactersByName(query: String): PagingSource<Int, DbRMCharacter>

    @RawQuery(observedEntities = [DbRMCharacter::class])
    fun getRMCharacters(query: SupportSQLiteQuery): PagingSource<Int, DbRMCharacter>

    @Query("SELECT * FROM rickAndMortyCharacters")
    fun getAllRMCharacters(): PagingSource<Int, DbRMCharacter>

    @Query("SELECT * FROM rickAndMortyCharacters WHERE id = :id")
    fun getRMCharacterById(id: Int): Flow<DbRMCharacter?>
}