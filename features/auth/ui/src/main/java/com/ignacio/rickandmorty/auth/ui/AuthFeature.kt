package com.ignacio.rickandmorty.auth.ui

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignacio.rickandmorty.auth.presentation.AuthViewModel
import com.ignacio.rickandmorty.kotlin_utils.extensions.getDebugOrProductionText
import com.ignacio.rickandmorty.resources.R
import com.ignacio.rickandmorty.ui_common.composables.ErrorBottomSheet

@Composable
fun AuthFeature(
    onUserSigned: () -> Unit,
    onGoogleSignInClick: () -> Unit,
) {
    val context = LocalContext.current.applicationContext
    val activity = LocalContext.current as Activity
    val viewModel: AuthViewModel = hiltViewModel(LocalContext.current as ViewModelStoreOwner)
    val state by viewModel.state.collectAsStateWithLifecycle()
    var bottomSheetError: String by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(
        key1 = state.userData,
        key2 = state.signInError,
    ) {
        if (state.isSignInSuccessful) {
            val message = if (state.userData?.username != null) {
                context.getString(R.string.sign_in_successful_feedback_with_user, state.userData?.username)
            } else {
                context.getString(R.string.sign_in_successful_feedback)
            }
            Toast.makeText(
                context,
                message,
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
        onGoogleSignInClick = onGoogleSignInClick,
        onGithubSignInClick = {
            viewModel.startGithubLogin { authUiClient, email ->
                authUiClient.startGitHubLogin(activity, email)
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
