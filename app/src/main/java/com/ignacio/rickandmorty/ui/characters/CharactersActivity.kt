package com.ignacio.rickandmorty.ui.characters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ignacio.rickandmorty.ui.character.navigation.CharactersFeature
import com.ignacio.rickandmorty.ui_common.theme.RickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
                CharactersFeature()
            }
        }
    }
}
