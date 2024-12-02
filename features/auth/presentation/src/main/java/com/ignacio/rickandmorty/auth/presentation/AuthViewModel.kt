package com.ignacio.rickandmorty.auth.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ignacio.rickandmorty.auth.domain.models.SignInResult
import com.ignacio.rickandmorty.kotlin_utils.extensions.isValidEmail
import com.ignacio.rickandmorty.kotlin_utils.ui.UiField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.error,
                status = Status.DONE,
            )
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    fun loading() {
        _state.update { it.copy(status = Status.LOADING) }
    }

    fun updateEmail(email: String) {
        val inputErrors = mutableListOf<String>()
        if (!email.trim().isValidEmail()) {
            inputErrors.add("Not a valid email")
        }
        _state.update { it.copy(userEmail = UiField(value = email, inputErrors = inputErrors)) }
    }
}