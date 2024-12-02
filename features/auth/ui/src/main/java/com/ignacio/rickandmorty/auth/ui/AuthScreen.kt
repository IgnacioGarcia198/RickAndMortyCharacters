package com.ignacio.rickandmorty.auth.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ignacio.rickandmorty.auth.presentation.AuthViewModel
import com.ignacio.rickandmorty.auth.presentation.SignInState
import com.ignacio.rickandmorty.auth.presentation.Status
import com.ignacio.rickandmorty.kotlin_utils.ui.isValid
import com.ignacio.rickandmorty.resources.R
import com.ignacio.rickandmorty.ui_common.composables.BasicInputField

@Composable
fun AuthScreen(
    state: SignInState,
    onGoogleSignInClick: () -> Unit,
    onGithubSignInClick: () -> Unit,
    onEmailChanged: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (state.status == Status.INITIAL) {
            Column {
                Button(onClick = onGoogleSignInClick) {
                    Text(text = stringResource(id = R.string.google_sign_in_action))
                }
                BasicInputField(
                    value = state.userEmail,
                    label = { Text(text = stringResource(id = R.string.email_text_field_hint)) },
                    onValueChange = onEmailChanged,
                )
                Button(onClick = onGithubSignInClick, enabled = state.userEmail.isValid) {
                    Text(text = stringResource(id = R.string.github_sign_in_action))
                }
            }
        } else {
            CircularProgressIndicator()
        }
    }
}