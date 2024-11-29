package com.ignacio.rickandmorty.main_navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ignacio.rickandmorty.auth.ui.AuthFeature
import com.ignacio.rickandmorty.network_monitor.ui.NetworkAwareBox
import com.ignacio.rickandmorty.ui.character.navigation.CharactersFeature
import kotlinx.serialization.Serializable

@Serializable
data object Login

// Route for nested graph
@Serializable
data object Characters

@Composable
fun MainNavigation() {
    NetworkAwareBox { snackbarHostState ->

        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Login,
            modifier = Modifier
        ) {
            composable<Login> {
                AuthFeature { navController.navigate(route = Characters) }
            }

            composable<Characters> {
                CharactersFeature(snackbarHostState)
            }
        }
    }
}