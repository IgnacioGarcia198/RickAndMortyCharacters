package com.ignacio.rickandmorty.characters.data.di

import com.ignacio.rickandmorty.characters.data.repository.RealRMCharactersRepository
import com.ignacio.rickandmorty.characters.domain.repository.RMCharactersRepository
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
