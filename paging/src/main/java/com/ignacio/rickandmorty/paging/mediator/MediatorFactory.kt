package com.ignacio.rickandmorty.paging.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator

interface MediatorFactory<Key : Any, Value : Any, Query : Any> {
    @OptIn(ExperimentalPagingApi::class)
    fun create(query: Query): RemoteMediator<Key, Value>
}
