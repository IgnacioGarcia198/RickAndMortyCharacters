package com.ignacio.rickandmorty.auth.presentation

import com.ignacio.rickandmorty.kotlin_utils.ui.UiField

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: Throwable? = null,
    val userEmail: UiField<String> = UiField(""),
    val status: Status = Status.INITIAL,
)

enum class Status {
    INITIAL, LOADING, DONE
}
