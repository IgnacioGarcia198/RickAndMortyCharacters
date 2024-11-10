package com.ignacio.rickandmorty.di_connector

import com.ignacio.rickandmorty.kotlin_utils.di.coroutines.CoroutinesModule
import com.ignacio.rickandmorty.framework.remote.di.NetworkModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [CoroutinesModule::class, NetworkModule::class])
@InstallIn(SingletonComponent::class)
interface ConnectorModule