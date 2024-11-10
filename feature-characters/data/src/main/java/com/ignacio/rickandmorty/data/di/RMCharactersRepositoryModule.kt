package com.ignacio.rickandmorty.data.di

import com.ignacio.rickandmorty.data.mediator.CharactersMediatorFactory
import com.ignacio.rickandmorty.data.mediator.RealCharactersMediatorFactory
import com.ignacio.rickandmorty.data.paging.CharactersPagerFactory
import com.ignacio.rickandmorty.data.paging.RealCharactersPagerFactory
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

    @Binds
    fun bindCharacterMediatorFactory(factory: RealCharactersMediatorFactory): CharactersMediatorFactory

    @Binds
    fun bindCharactersPagerFactory(factory: RealCharactersPagerFactory): CharactersPagerFactory
}
