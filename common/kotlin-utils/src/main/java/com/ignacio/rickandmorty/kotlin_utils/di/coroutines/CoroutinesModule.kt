package com.ignacio.rickandmorty.kotlin_utils.di.coroutines

import com.ignacio.rickandmorty.kotlin_utils.coroutines.DefaultDispatcherProvider
import com.ignacio.rickandmorty.kotlin_utils.coroutines.DispatcherProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
@Suppress("unused")
interface CoroutinesModule {
    @Binds
    @Singleton
    fun bindDispatcherProvider(provider: DefaultDispatcherProvider): DispatcherProvider
}
