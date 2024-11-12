package com.ignacio.rickandmorty.data.paging

import androidx.paging.Pager
import androidx.paging.PagingSource

interface CharactersPagerFactory<Key : Any, Value : Any> {
    fun create(
        updateFromRemote: suspend (page: Int, shouldClearLocalCache: Boolean) -> Result<Boolean>,
        pagingSourceFactory: () -> PagingSource<Key, Value>
    ): Pager<Key, Value>
}