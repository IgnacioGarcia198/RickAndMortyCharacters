package com.ignacio.rickandmorty.ui.characters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.ignacio.rickandmorty.auth.ui.AuthFeature
import com.ignacio.rickandmorty.ui_common.theme.AppTheme
import com.ignacio.rickandmorty.ui.character.navigation.CharactersFeature
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        //Firebase.analytics.logEvent("APPSTARTED", null) // FAILING: SEE https://github.com/firebase/firebase-android-sdk/issues/361
        enableEdgeToEdge()
        setContent {
            AppTheme {
                //CharactersFeature()
                AuthFeature()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        println("########################### CURRENT USER: $currentUser")
    }
}
