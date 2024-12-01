package com.ignacio.rickandmorty.auth.domain.models

data class SignInResult(
    val data: UserData?,
    val error: Throwable?,
)

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)
