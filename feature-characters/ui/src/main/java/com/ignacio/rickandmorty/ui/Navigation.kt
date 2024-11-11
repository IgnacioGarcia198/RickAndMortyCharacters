package com.ignacio.rickandmorty.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.ignacio.rickandmorty.presentation.characterlist.viewmodel.RMCharactersViewModel
import com.ignacio.rickandmorty.presentation.characterlist.viewmodel.RMCharactersViewModelContract
import com.ignacio.rickandmorty.ui_common.theme.RickAndMortyTheme

@Composable
fun CharactersFeature() {
    val viewModel: RMCharactersViewModelContract = hiltViewModel<RMCharactersViewModel>()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val characters = viewModel.pagingDataFlow.collectAsLazyPagingItems()
        //TestScreen(characters = characters)
        BeerScreen(characters = characters)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RickAndMortyTheme {
        Greeting("Android")
    }
}