package com.ignacio.rickandmorty.kotlin_utils.paging

data class TestPagedData<T: Any>(
    val value: T
): PagedData<T> {
    override fun <U : Any> map(block: (T) -> U): PagedData<U> {
        return TestPagedData(block(value))
    }
}