package com.ignacio.rickandmorty.data.datasources.local

import androidx.paging.PagingSource
import com.ignacio.rickandmorty.data.models.LocalRMCharacter
import com.ignacio.rickandmorty.data.models.RMCharacter

interface CharactersLocalDataSource {
    suspend fun upsertAll(characters: List<RMCharacter>, clear: Boolean = false, query: String = "")
    fun getRMCharacters(query: String): PagingSource<Int, LocalRMCharacter>
}