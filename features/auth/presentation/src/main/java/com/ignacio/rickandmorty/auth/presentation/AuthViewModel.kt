package com.ignacio.rickandmorty.auth.presentation

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacio.rickandmorty.auth.auth.google.AuthUiClient
import com.ignacio.rickandmorty.auth.domain.models.UserData
import com.ignacio.rickandmorty.kotlin_utils.extensions.isValidEmail
import com.ignacio.rickandmorty.kotlin_utils.ui.UiField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class AuthViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authUiClient: AuthUiClient,
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun resetState() {
        _state.update { SignInState() }
    }

    private fun loading() {
        _state.update { it.copy(status = Status.LOADING) }
    }

    fun updateEmail(email: String) {
        val inputErrors = mutableListOf<String>()
        if (!email.trim().isValidEmail()) {
            inputErrors.add("Not a valid email")
        }
        _state.update { it.copy(userEmail = UiField(value = email, inputErrors = inputErrors)) }
    }

    fun startGoogleLogin(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        viewModelScope.launch {
            loading()
            try {
                val intentSender = authUiClient.signInGoogle()
                launcher.launch(IntentSenderRequest.Builder(intentSender).build())
            } catch (e: Exception) {
                e.printStackTrace()
                if (e is CancellationException) throw e
                _state.update { it.copy(userData = null, isSignInSuccessful = false, signInError = e) }
            }
        }
    }

    fun handleSignInResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            viewModelScope.launch {
                authUiClient.signInGoogleWithIntent(result.data!!)
                    .updateState()
            }
        }
    }

    fun startGithubLogin(loginCall: suspend (AuthUiClient, String) -> Result<UserData?>) {
        viewModelScope.launch {
            loading()
            loginCall(authUiClient, state.value.userEmail.value)
                .updateState()
        }
    }

    fun getSignedInUser(): UserData? = authUiClient.getSignedInUser()

    private fun Result<UserData?>.updateState() = onSuccess { userData ->
        _state.update { it.copy(userData = userData, isSignInSuccessful = true, signInError = null) }
    }.onFailure { e ->
        _state.update { it.copy(userData = null, isSignInSuccessful = false, signInError = e) }
    }
}
