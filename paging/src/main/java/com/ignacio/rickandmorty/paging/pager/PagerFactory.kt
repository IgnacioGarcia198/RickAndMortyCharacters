package com.ignacio.rickandmorty.paging.pager

import androidx.paging.PagingSource
import com.ignacio.rickandmorty.kotlin_utils.paging.PagerContract

interface PagerFactory<Key : Any, Value : Any> {
    fun create(
        updateFromRemote: suspend (page: Key, shouldClearLocalCache: Boolean) -> Result<Boolean>,
        pagingSourceFactory: () -> PagingSource<Key, Value>,
    ): PagerContract<Key, Value>
}
