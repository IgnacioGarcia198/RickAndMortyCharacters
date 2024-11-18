package com.ignacio.rickandmorty.di_connector

import com.ignacio.rickandmorty.data.di.RMCharactersRepositoryModule
import com.ignacio.rickandmorty.data.paging.di.DataPagingModule
import com.ignacio.rickandmorty.framework.remote.di.NetworkModule
import com.ignacio.rickandmorty.kotlin_utils.di.coroutines.CoroutinesModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        CoroutinesModule::class,
        RMCharactersRepositoryModule::class,
        DataPagingModule::class,
    ]
)
@InstallIn(SingletonComponent::class)
interface ConnectorModule