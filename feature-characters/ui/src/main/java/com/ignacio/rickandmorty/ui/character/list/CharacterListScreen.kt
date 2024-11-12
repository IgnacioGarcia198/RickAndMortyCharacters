package com.ignacio.rickandmorty.ui.character.list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ignacio.rickandmorty.presentation.character.list.viewmodel.RMCharactersViewModel
import com.ignacio.rickandmorty.presentation.character.list.viewmodel.RMCharactersViewModelContract
import com.ignacio.rickandmorty.presentation.character.models.UiRMCharacter

@Composable
fun CharacterListScreen(
    searchText: String,
    modifier: Modifier = Modifier,
    viewModel: RMCharactersViewModelContract = hiltViewModel<RMCharactersViewModel>(),
    onCharacterClick: (id: Int) -> Unit = {},
) {
    val characters: LazyPagingItems<UiRMCharacter> =
        viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val context = LocalContext.current
    LaunchedEffect(key1 = characters.loadState) {
        if (characters.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (characters.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    LaunchedEffect(key1 = searchText) {
        viewModel.setQuery(searchText)
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (characters.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(characters.itemCount) { index ->
                    characters[index]?.let { character ->
                        //onCharacterClicked(character.id)
                        RMCharacterItem(character = character, onCharacterClick = onCharacterClick)
                    }
                }
                item {
                    if (characters.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}