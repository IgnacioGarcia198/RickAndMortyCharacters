package com.ignacio.rickandmorty.ui.character.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ignacio.rickandmorty.resources.R
import com.ignacio.rickandmorty.ui.character.detail.CharacterDetailScreen
import com.ignacio.rickandmorty.ui.character.list.CharacterListScreen
import com.ignacio.rickandmorty.ui.theme.Pink80
import kotlinx.serialization.Serializable

@Serializable
data class RMCharacterDetail(val id: Int)

@Serializable
data object RMCharacterList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersFeature() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            val titleText = stringResource(id = R.string.app_name)
            TopAppBar(
                title = { Text(titleText) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Pink80),
                modifier = Modifier.semantics { contentDescription = "Current screen: $titleText" }
            )
        },
    ) { paddingValues ->
        SideEffect {
            Log.d("RecompositionTracker", "TaskApp Scaffold was recomposed")
        }
        NavHost(
            navController = navController,
            startDestination = RMCharacterList,
            modifier = Modifier
                .padding(paddingValues)
                .padding(all = 8.dp)
        ) {
            composable<RMCharacterList> {
                CharacterListScreen {
                    navController.navigate(route = RMCharacterDetail(id = it))
                }
            }
            composable<RMCharacterDetail> { backStackEntry ->
                val rmCharacterDetail: RMCharacterDetail = backStackEntry.toRoute()
                val id = rmCharacterDetail.id
                CharacterDetailScreen(id = id)
            }
        }
    }
}