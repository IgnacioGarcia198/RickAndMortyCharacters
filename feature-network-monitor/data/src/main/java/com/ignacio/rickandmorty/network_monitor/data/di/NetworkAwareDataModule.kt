package com.ignacio.rickandmorty.network_monitor.data.di

import com.ignacio.rickandmorty.network_monitor.data.repository.RealNetworkAwareRepository
import com.ignacio.rickandmorty.network_monitor.domain.repository.NetworkAwareRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
@Suppress("unused")
interface NetworkAwareDataModule {
    @Binds
    @Singleton
    fun bindNetworkAwareRepository(repository: RealNetworkAwareRepository): NetworkAwareRepository
}