package com.ignacio.rickandmorty.framework.local.di

import com.ignacio.rickandmorty.data.datasources.local.CharactersLocalDataSource
import com.ignacio.rickandmorty.framework.local.datasource.RealCharactersLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataSourceModule {
    @Binds
    fun bindCharactersLocalDataSource(dataSource: RealCharactersLocalDataSource): CharactersLocalDataSource
}