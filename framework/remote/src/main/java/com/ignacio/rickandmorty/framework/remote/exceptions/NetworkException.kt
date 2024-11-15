package com.ignacio.rickandmorty.framework.remote.exceptions

import io.ktor.http.HttpStatusCode

private const val DEFAULT_CODE = -1
private const val DEFAULT_DESCRIPTION = "empty"

data class NetworkException(
    val errorCode: Int = DEFAULT_CODE,
    val errorDescription: String = DEFAULT_DESCRIPTION,
    override val cause: Throwable? = null,
) : Exception("Network exception. Code: $errorCode, description: $errorDescription", cause) {
    constructor(status: HttpStatusCode?, cause: Throwable?) : this(
        errorCode = status?.value ?: DEFAULT_CODE,
        errorDescription = status?.description ?: DEFAULT_DESCRIPTION,
        cause = cause
    )
}
