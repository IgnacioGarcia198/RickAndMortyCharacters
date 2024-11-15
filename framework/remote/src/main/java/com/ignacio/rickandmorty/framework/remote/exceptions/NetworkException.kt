package com.ignacio.rickandmorty.framework.remote.exceptions

data class NetworkException(
    val errorCode: Int = -1,
    val errorDescription: String = "empty",
    override val cause: Throwable? = null,
): Exception("Network exception. Code: $errorCode, description: $errorDescription", cause)
