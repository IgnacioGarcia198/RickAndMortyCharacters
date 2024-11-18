package com.ignacio.rickandmorty.ui.character.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ignacio.rickandmorty.ui.character.detail.CharacterDetailScreen
import com.ignacio.rickandmorty.ui.character.list.CharacterListScreen
import kotlinx.serialization.Serializable

@Serializable
data class RMCharacterDetail(val id: Int)

@Serializable
data object RMCharacterList

@Composable
fun CharactersFeature() {
    NetworkAwareBox { snackbarHostState ->

        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = RMCharacterList,
            modifier = Modifier
        ) {
            composable<RMCharacterList> {
                CharacterListScreen(snackbarHostState) {
                    navController.navigate(route = RMCharacterDetail(id = it))
                }
            }
            composable<RMCharacterDetail> { backStackEntry ->
                val rmCharacterDetail: RMCharacterDetail = backStackEntry.toRoute()
                val id = rmCharacterDetail.id
                CharacterDetailScreen(id = id) {
                    navController.popBackStack()
                }
            }
        }
    }
}

@Composable
fun NetworkAwareBox(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (SnackbarHostState) -> Unit,
) {
    var isNetworkAvailable by remember {
        mutableStateOf(false)
    }
    if (!isNetworkAvailable) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                message = "No network connection",
                actionLabel = "Dismiss"
            )
        }
    }
    Box(modifier = modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackbarHostState,
            //modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp) // Position the snackbar
        )
        content(snackbarHostState)
    }
}


