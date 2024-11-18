package com.ignacio.rickandmorty.ui.character.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ignacio.rickandmorty.network_monitor.ui.NetworkAwareBox
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
