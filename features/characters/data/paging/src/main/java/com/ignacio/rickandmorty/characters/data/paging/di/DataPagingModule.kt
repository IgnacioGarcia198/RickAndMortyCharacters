package com.ignacio.rickandmorty.characters.data.paging.di

import com.ignacio.rickandmorty.characters.data.paging.mediator.RealCharactersMediatorFactory
import com.ignacio.rickandmorty.characters.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.characters.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.characters.data.paging.pager.RealCharactersPagerFactory
import com.ignacio.rickandmorty.characters.data.paging.repository.RealRMCharactersPagingRepository
import com.ignacio.rickandmorty.characters.data.paging.updater.RealRemoteLocalUpdater
import com.ignacio.rickandmorty.characters.data.paging.updater.RemoteLocalUpdater
import com.ignacio.rickandmorty.characters.domain.repository.RMCharactersPagingRepository
import com.ignacio.rickandmorty.paging.mediator.MediatorFactory
import com.ignacio.rickandmorty.paging.pager.PagerFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Suppress("unused")
@Module
interface DataPagingModule {
    @Binds
    @Singleton
    fun bindRepository(repository: RealRMCharactersPagingRepository): RMCharactersPagingRepository

    @Binds
    fun bindCharacterMediatorFactory(factory: RealCharactersMediatorFactory): MediatorFactory<Int, LocalRMCharacter, CharacterQueryCriteria>

    @Binds
    fun bindCharactersPagerFactory(factory: RealCharactersPagerFactory): PagerFactory<Int, LocalRMCharacter, CharacterQueryCriteria>

    @Binds
    fun bindRemoteLocalUpdater(updater: RealRemoteLocalUpdater): RemoteLocalUpdater<CharacterQueryCriteria, Int>
}
