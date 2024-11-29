package com.ignacio.rickandmorty.framework.local.di

import com.ignacio.rickandmorty.characters.data.datasources.local.CharactersLocalDataSource
import com.ignacio.rickandmorty.characters.data.paging.datasource.local.CharactersLocalPagingDataSource
import com.ignacio.rickandmorty.framework.local.datasource.RealCharactersLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface LocalDataSourceModule {
    @Binds
    @Singleton
    fun bindCharactersLocalDataSource(dataSource: RealCharactersLocalDataSource): CharactersLocalDataSource

    @Binds
    @Singleton
    fun bindCharactersPagingLocalDataSource(dataSource: RealCharactersLocalDataSource): CharactersLocalPagingDataSource
}