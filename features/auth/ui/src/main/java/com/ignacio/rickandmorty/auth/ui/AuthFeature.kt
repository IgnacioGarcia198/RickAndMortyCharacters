package com.ignacio.rickandmorty.auth.ui

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignacio.rickandmorty.auth.auth.di.GithubAuthClientEntryPoint
import com.ignacio.rickandmorty.auth.auth.di.GoogleAuthClientEntryPoint
import com.ignacio.rickandmorty.auth.presentation.AuthViewModel
import com.ignacio.rickandmorty.kotlin_utils.extensions.getDebugOrProductionText
import com.ignacio.rickandmorty.resources.R
import com.ignacio.rickandmorty.ui_common.composables.ErrorBottomSheet
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch

@Composable
fun AuthFeature(
    onUserSigned: () -> Unit,
) {
    val context = LocalContext.current.applicationContext
    val viewModel: AuthViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val entryPoint =
        EntryPointAccessors.fromApplication(context, GoogleAuthClientEntryPoint::class.java)
    val githubEntryPoint =
        EntryPointAccessors.fromApplication(context, GithubAuthClientEntryPoint::class.java)
    val googleAuthUiClient = entryPoint.googleAuthUiClient()
    val githubAuthUiClient = githubEntryPoint.githubUiClient()

    val coroutineScope = rememberCoroutineScope()
    var bottomSheetError: String by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as Activity

    LaunchedEffect(key1 = Unit) {
        if (googleAuthUiClient.getSignedInUser() != null) {
            // navigate to the app content
            onUserSigned()
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (githubAuthUiClient.getSignedInUser() != null) {
            // navigate to the app content
            onUserSigned()
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                coroutineScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )

    LaunchedEffect(
        key1 = state.isSignInSuccessful,
        key2 = state.signInError,
    ) {
        if (state.isSignInSuccessful) {
            Toast.makeText(
                context,
                context.getString(R.string.sign_in_successful_feedback),
                Toast.LENGTH_LONG
            ).show()

            onUserSigned() // here navigate to app content
            viewModel.resetState()
        } else {
            state.signInError?.let {
                bottomSheetError = it.getDebugOrProductionText()
            }
        }
    }

    AuthScreen(
        state = state,
        onGoogleSignInClick = {
            coroutineScope.launch {
                viewModel.loading()
                val result = googleAuthUiClient.signIn()
                result.onSuccess { signIntentSender ->
                    signIntentSender?.let {
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                it
                            ).build()
                        )
                    }
                }.onFailure {
                    bottomSheetError = it.getDebugOrProductionText()
                }
            }
        },
        onGithubSignInClick = {
            coroutineScope.launch {
                viewModel.loading()
                val signInResult = githubAuthUiClient.startGitHubLogin(activity = activity, state.userEmail.value)
                viewModel.onSignInResult(signInResult)
            }
        },
        onEmailChanged = viewModel::updateEmail
    )

    ErrorBottomSheet(
        errorText = bottomSheetError,
        onClose = {
            bottomSheetError = ""
        }
    )
}
