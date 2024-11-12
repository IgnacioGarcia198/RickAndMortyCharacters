package com.ignacio.rickandmorty.ui.character.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ignacio.rickandmorty.presentation.character.list.viewmodel.RMCharactersViewModel
import com.ignacio.rickandmorty.presentation.character.list.viewmodel.RMCharactersViewModelContract
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
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    //val viewModel: RMCharactersViewModelContract = hiltViewModel<RMCharactersViewModel>()
    var showingSearchTextField by remember {
        mutableStateOf(false)
    }
    var searchText by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            val titleText = stringResource(id = R.string.app_name)
            TopAppBar(
                title = {
                    if (showingSearchTextField && currentBackStackEntry.isRMCharacterList) {
                        OutlinedTextField(
                            value = searchText,
                            onValueChange = {
                                searchText = it
                            },
                            shape = MaterialTheme.shapes.small.copy(
                                bottomEnd = MaterialTheme.shapes.small.bottomEnd,
                                bottomStart = MaterialTheme.shapes.small.bottomStart,
                                topStart = MaterialTheme.shapes.small.bottomStart,
                                topEnd = MaterialTheme.shapes.small.bottomEnd
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            placeholder = { Text(stringResource(R.string.top_app_bar_search_hint)) },
                        )
                    } else {
                        Text(titleText)
                    } 
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Pink80),
                modifier = Modifier.semantics { contentDescription = "Current screen: $titleText" },
                navigationIcon = {
                    if (currentBackStackEntry?.destination?.route?.startsWith(RMCharacterDetail::class.java.name) == true) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "ahshh"
                            )
                        }
                    }
                },
                actions = {
                    if (currentBackStackEntry?.destination?.route == RMCharacterList.javaClass.name) {
                        IconButton(onClick = {
                            if (showingSearchTextField) {
                                showingSearchTextField = false
                                searchText = ""
                            } else {
                                showingSearchTextField = true
                            }
                        }) {
                            Icon(
                                imageVector = if (showingSearchTextField) Icons.Default.Close else Icons.Default.Search,
                                contentDescription = "jjjj"
                            )
                        }
                    }
                }
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
                CharacterListScreen(searchText = searchText) {
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

val NavBackStackEntry?.isRMCharacterList: Boolean get() = this?.destination?.route == RMCharacterList.javaClass.name

//@Preview

