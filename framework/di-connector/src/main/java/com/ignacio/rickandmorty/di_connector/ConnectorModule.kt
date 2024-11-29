package com.ignacio.rickandmorty.di_connector

import com.ignacio.rickandmorty.characters.data.di.RMCharactersRepositoryModule
import com.ignacio.rickandmorty.characters.data.paging.di.DataPagingModule
import com.ignacio.rickandmorty.kotlin_utils.di.coroutines.CoroutinesModule
import com.ignacio.rickandmorty.network_monitor.data.di.NetworkAwareDataModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        CoroutinesModule::class,
        RMCharactersRepositoryModule::class,
        DataPagingModule::class,
        NetworkAwareDataModule::class,
    ]
)
@InstallIn(SingletonComponent::class)
interface ConnectorModule