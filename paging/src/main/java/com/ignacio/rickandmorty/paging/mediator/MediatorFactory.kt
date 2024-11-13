package com.ignacio.rickandmorty.paging.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator

interface MediatorFactory<Key : Any, Value : Any> {
    @OptIn(ExperimentalPagingApi::class)
    fun create(
        updateFromRemote: suspend (page: Key, shouldClearLocalCache: Boolean) -> Result<Boolean>,
    ): RemoteMediator<Key, Value>
}
