package com.ignacio.rickandmorty.data.di

import com.ignacio.rickandmorty.data.repository.RealRMCharactersRepository
import com.ignacio.rickandmorty.domain.repository.RMCharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindRepository(repository: RealRMCharactersRepository): RMCharactersRepository
}