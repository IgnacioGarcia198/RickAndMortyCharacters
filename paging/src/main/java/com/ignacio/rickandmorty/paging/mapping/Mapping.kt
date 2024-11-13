package com.ignacio.rickandmorty.paging.mapping

import androidx.paging.PagingData
import com.ignacio.rickandmorty.kotlin_utils.paging.PagedData
import com.ignacio.rickandmorty.paging.models.AndroidPagedData

fun <Value : Any> PagedData<Value>.toAndroid(): PagingData<Value> =
    if (this is AndroidPagedData<*>) (this as AndroidPagedData<Value>).pagingData else throw IllegalArgumentException()