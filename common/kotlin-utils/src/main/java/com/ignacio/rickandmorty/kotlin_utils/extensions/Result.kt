package com.ignacio.rickandmorty.kotlin_utils.extensions

fun <T> Result<T>.mapError(block: (throwable: Throwable) -> Throwable): Result<T> =
    when {
        isFailure -> Result.failure(block(exceptionOrNull()!!))
        else -> this
    }

fun <T> Result<T>.recoverIfPossible(block: (throwable: Throwable, result: Result<T>) -> Result<T>): Result<T> =
    when {
        isFailure -> {
            block(exceptionOrNull()!!, this)
        }
        else -> this
    }
