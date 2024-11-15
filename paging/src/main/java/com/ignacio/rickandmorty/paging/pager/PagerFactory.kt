package com.ignacio.rickandmorty.paging.pager

import com.ignacio.rickandmorty.kotlin_utils.paging.PagerContract

interface PagerFactory<Key : Any, Value : Any, Query : Any> {
    fun create(
        queryCriteria: Query,
    ): PagerContract<Key, Value>
}
