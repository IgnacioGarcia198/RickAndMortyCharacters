package com.ignacio.rickandmorty.framework.remote.di

import com.ignacio.rickandmorty.data.datasources.remote.RickAndMortyApi
import com.ignacio.rickandmorty.framework.remote.api.RealRickAndMortyApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@Suppress("unused")
interface NetworkModule {
    @Binds
    @Singleton
    fun bindRickAndMortyApi(api: RealRickAndMortyApi): RickAndMortyApi

    companion object {
        @Provides
        fun provideHttpClient(): HttpClient {
            return HttpClient(CIO) {
                install(Logging) {
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
                }
            }
        }
    }
}
