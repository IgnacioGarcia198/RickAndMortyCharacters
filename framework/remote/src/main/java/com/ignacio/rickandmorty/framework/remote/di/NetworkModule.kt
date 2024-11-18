package com.ignacio.rickandmorty.framework.remote.di

import android.app.Application
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import com.ignacio.rickandmorty.data.paging.datasource.remote.RickAndMortyApi
import com.ignacio.rickandmorty.framework.remote.api.RealRickAndMortyApi
import com.ignacio.rickandmorty.framework.remote.monitor.RealConnectivityMonitor
import com.ignacio.rickandmorty.network.ConnectivityMonitor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
interface NetworkModule {
    @Binds
    @Singleton
    fun bindRickAndMortyApi(api: RealRickAndMortyApi): RickAndMortyApi

    @Binds
    @Singleton
    fun bindConnectivityMonitor(monitor: RealConnectivityMonitor): ConnectivityMonitor

    companion object {
        @Provides
        fun provideConnectivityManager(
            application: Application
        ): ConnectivityManager = ContextCompat.getSystemService(
            application, ConnectivityManager::class.java
        )!!

        @Provides
        fun provideHttpClient(engine: HttpClientEngine): HttpClient {
            return HttpClient(engine) {
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

        @Provides
        fun provideEngine(): HttpClientEngine {
            return CIO.create()
        }
    }
}
