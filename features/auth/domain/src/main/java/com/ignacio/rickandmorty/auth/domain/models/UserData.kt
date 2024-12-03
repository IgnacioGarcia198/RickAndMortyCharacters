package com.ignacio.rickandmorty.auth.domain.models

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)
