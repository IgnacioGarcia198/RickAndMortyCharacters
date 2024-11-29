package com.ignacio.rickandmorty.auth.presentation

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: Throwable? = null,
    val status: Status = Status.INITIAL,
)

enum class Status {
    INITIAL, LOADING, DONE
}
