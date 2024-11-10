package com.ignacio.rickandmorty.framework.remote.api

import com.ignacio.rickandmorty.framework.remote.di.NetworkModule
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val api = RealRickAndMortyApi(NetworkModule.provideHttpClient())
    val response = api.getCharacters()
    println(response)
}