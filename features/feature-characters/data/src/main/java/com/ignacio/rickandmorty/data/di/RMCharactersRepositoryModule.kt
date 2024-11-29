package com.ignacio.rickandmorty.data.di

import com.ignacio.rickandmorty.data.repository.RealRMCharactersRepository
import com.ignacio.rickandmorty.domain.repository.RMCharactersRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Suppress("unused")
@Module
interface RMCharactersRepositoryModule {
    @Binds
    @Singleton
    fun bindRepository(repository: RealRMCharactersRepository): RMCharactersRepository
}
