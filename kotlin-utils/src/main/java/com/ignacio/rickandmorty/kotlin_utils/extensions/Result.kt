package com.ignacio.rickandmorty.kotlin_utils.extensions

fun <T> Result<T>.mapError(block: (throwable: Throwable) -> Throwable): Result<T> =
    when {
        isFailure -> Result.failure(block(exceptionOrNull()!!))
        else -> this
    }
