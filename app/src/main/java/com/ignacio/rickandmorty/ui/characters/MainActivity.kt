package com.ignacio.rickandmorty.ui.characters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.ignacio.rickandmorty.auth.presentation.AuthViewModel
import com.ignacio.rickandmorty.main_navigation.ui.MainNavigation
import com.ignacio.rickandmorty.ui_common.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            authViewModel.handleSignInResult(result)
        }

    private fun startGoogleLogin() {
        authViewModel.loading()
        authViewModel.startGoogleLogin(launcher)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MainNavigation(
                    onGoogleSignInClick = {
                        startGoogleLogin()
                    }
                )
            }
        }
    }
}
