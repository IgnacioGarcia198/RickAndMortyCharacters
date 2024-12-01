package com.ignacio.rickandmorty.auth.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ignacio.rickandmorty.auth.presentation.SignInState
import com.ignacio.rickandmorty.auth.presentation.Status
import com.ignacio.rickandmorty.resources.R

@Composable
fun AuthScreen(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (state.status == Status.INITIAL) {
            Button(onClick = onSignInClick) {
                Text(text = stringResource(id = R.string.sign_in_action))
            }
        } else {
            CircularProgressIndicator()
        }
    }
}