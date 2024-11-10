package com.ignacio.rickandmorty.network.api

import com.ignacio.rickandmorty.network.di.NetworkModule
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val api = RealRickAndMortyApi(NetworkModule.provideHttpClient())
    val response = api.getCharacters()
    println(response)
}