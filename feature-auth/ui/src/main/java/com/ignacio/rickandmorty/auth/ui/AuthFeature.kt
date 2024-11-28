package com.ignacio.rickandmorty.auth.ui

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.ignacio.rickandmorty.auth.presentation.AuthViewModel
import com.ignacio.rickandmorty.auth.presentation.GoogleAuthUiClient
import kotlinx.coroutines.launch

@Composable
fun AuthFeature() {
    val context = LocalContext.current.applicationContext
    val viewModel: AuthViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            oneTapClient = Identity.getSignInClient(context),
            auth = Firebase.auth,
        )
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        if (googleAuthUiClient.getSignedInUser() != null) {
            //navController.navigate("profile")
            // navigate to the app content
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

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {
            Toast.makeText(
                context,
                "Sign in successful",
                Toast.LENGTH_LONG
            ).show()

            //navController.navigate("profile") // here navigate to app content
            viewModel.resetState()
        } else {
            state.signInError?.let {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    AuthScreen(
        state = state,
        onSignInClick = {
            coroutineScope.launch {
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
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    )
}
