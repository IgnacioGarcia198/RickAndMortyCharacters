package com.ignacio.rickandmorty.kotlin_utils.extensions

import com.ignacio.rickandmorty.kotlin_utils.paging.PagedData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun <T, U> Flow<T>.asResultFlow(transform: (T) -> U): Flow<Result<U>> =
    map { Result.success(transform(it)) }
        .catch { e -> emit(Result.failure(e)) }

fun <T> Flow<T>.asResultFlow(): Flow<Result<T>> =
    map { Result.success(it) }
        .catch { e -> emit(Result.failure(e)) }

fun <T, U> Flow<Result<T>>.mapResultFlow(block: (T) -> U): Flow<Result<U>> =
    map { result -> result.map(block) }

fun <T: Any, U: Any> Flow<PagedData<T>>.mapPagedFlow(block: (T) -> U): Flow<PagedData<U>> = map { data -> data.map(block) }
