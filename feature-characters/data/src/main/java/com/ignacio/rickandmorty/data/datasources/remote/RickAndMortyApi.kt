package com.ignacio.rickandmorty.data.datasources.remote

import com.ignacio.rickandmorty.data.models.RMCharacters

interface RickAndMortyApi {
    suspend fun getCharacters(): Result<RMCharacters>
}