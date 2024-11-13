package com.ignacio.rickandmorty.paging.pager

import androidx.paging.Pager
import com.ignacio.rickandmorty.kotlin_utils.paging.PagedData
import com.ignacio.rickandmorty.kotlin_utils.paging.PagerContract
import com.ignacio.rickandmorty.paging.models.AndroidPagedData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AndroidPager<Key : Any, Value : Any>(
    private val pager: Pager<Key, Value>
) : PagerContract<Key, Value> {
    override val flow: Flow<PagedData<Value>> = pager.flow.map { AndroidPagedData(it) }
}
