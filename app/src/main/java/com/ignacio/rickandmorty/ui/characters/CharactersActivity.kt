package com.ignacio.rickandmorty.ui.characters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.ignacio.rickandmorty.ui_common.theme.AppTheme
import com.ignacio.rickandmorty.ui.character.navigation.CharactersFeature
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Firebase.analytics.logEvent("APPSTARTED", null) // FAILING: SEE https://github.com/firebase/firebase-android-sdk/issues/361
        enableEdgeToEdge()
        setContent {
            AppTheme {
                CharactersFeature()
            }
        }
    }
}
