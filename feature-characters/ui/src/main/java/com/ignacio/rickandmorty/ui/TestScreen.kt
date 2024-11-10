package com.ignacio.rickandmorty.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ignacio.rickandmorty.domain.models.RMCharacter

@Composable
fun TestScreen(
    characters: LazyPagingItems<RMCharacter>
) {
    LazyColumn {
        items(characters.itemCount) { index ->
            Text(
                text = characters[index]!!.name,
                color = MaterialTheme.colorScheme.primary
            )
        }
        characters.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = characters.loadState.refresh as LoadState.Error
                    item {
                        Text(text = error.error.localizedMessage!!)
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier) }
                }

                loadState.append is LoadState.Error -> {
                    val error = characters.loadState.append as LoadState.Error
                    item {
                        Text(text = error.error.localizedMessage!!)
                    }
                }
            }
        }
    }
}