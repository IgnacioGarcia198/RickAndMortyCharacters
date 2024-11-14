package com.ignacio.rickandmorty.data.paging.di

import com.ignacio.rickandmorty.data.paging.mediator.RealCharactersMediatorFactory
import com.ignacio.rickandmorty.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.data.paging.pager.RealCharactersPagerFactory
import com.ignacio.rickandmorty.data.paging.repository.RealRMCharactersPagingRepository
import com.ignacio.rickandmorty.data.paging.updater.RealRemoteLocalUpdater
import com.ignacio.rickandmorty.data.paging.updater.RemoteLocalUpdater
import com.ignacio.rickandmorty.domain.repository.RMCharactersPagingRepository
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
    fun bindCharacterMediatorFactory(factory: RealCharactersMediatorFactory): MediatorFactory<Int, LocalRMCharacter>

    @Binds
    fun bindCharactersPagerFactory(factory: RealCharactersPagerFactory): PagerFactory<Int, LocalRMCharacter>

    @Binds
    fun bindRemoteLocalUpdater(updater: RealRemoteLocalUpdater): RemoteLocalUpdater
}
