package com.ignacio.rickandmorty.characters.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ignacio.rickandmorty.characters.ui.detail.CharacterDetailScreen
import com.ignacio.rickandmorty.characters.ui.list.CharacterListScreen
import kotlinx.serialization.Serializable

@Serializable
data class RMCharacterDetail(val id: Int)

@Serializable
data object RMCharacterList

@Composable
fun CharactersFeature(
    snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
) {
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
