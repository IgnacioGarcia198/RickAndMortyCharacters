package com.ignacio.rickandmorty.kotlin_utils.paging

import kotlinx.coroutines.flow.Flow

class TestPager<T : Any, U : Any>(
    override val flow: Flow<TestPagedData<U>>
) : PagerContract<T, U>
