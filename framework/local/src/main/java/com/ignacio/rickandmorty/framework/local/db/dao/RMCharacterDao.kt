package com.ignacio.rickandmorty.framework.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ignacio.rickandmorty.framework.local.models.DbRMCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface RMCharacterDao {
    @Upsert
    fun upsertAll(characters: List<DbRMCharacter>)

    @Query("DELETE FROM rickAndMortyCharacters WHERE name LIKE :query")
    fun deleteByQuery(query: String)

    @Query("SELECT * FROM rickAndMortyCharacters WHERE name LIKE :query")
    fun getRMCharacters(query: String): PagingSource<Int, DbRMCharacter>

    @Query("SELECT * FROM rickAndMortyCharacters")
    fun getAllRMCharacters(): PagingSource<Int, DbRMCharacter>
    @Query("SELECT * FROM rickAndMortyCharacters WHERE id = :id")
    fun getRMCharacterById(id: Int): Flow<DbRMCharacter?>
}