package com.ignacio.rickandmorty.paging.models

import androidx.paging.PagingData
import androidx.paging.map
import com.ignacio.rickandmorty.kotlin_utils.paging.PagedData

class AndroidPagedData<Value : Any>(
    val pagingData: PagingData<Value>
) : PagedData<Value> {
    override fun <U : Any> map(block: (Value) -> U): PagedData<U> {
        return AndroidPagedData(pagingData.map(block))
    }
}