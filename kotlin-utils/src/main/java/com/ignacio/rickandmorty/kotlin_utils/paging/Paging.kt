package com.ignacio.rickandmorty.kotlin_utils.paging

import kotlinx.coroutines.flow.Flow

interface PagerContract<Key : Any, Value : Any> {
    val flow: Flow<PagedData<Value>>
}

interface PagedData<Value: Any> {
    fun <U: Any> map(block: (Value) -> U): PagedData<U>
}
