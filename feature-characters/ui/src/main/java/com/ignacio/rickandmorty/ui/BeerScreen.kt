package com.ignacio.rickandmorty.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ignacio.rickandmorty.domain.models.RMCharacter
import com.ignacio.rickandmorty.presentation.models.UiRMCharacter

@Composable
fun BeerScreen(
    characters: LazyPagingItems<UiRMCharacter>
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = characters.loadState) {
        if(characters.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (characters.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if(characters.loadState.refresh is LoadState.Loading) {
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
                    Text(text = characters[index]?.name.orEmpty())
                    //if(beer != null) {
                    //    BeerItem(
                    //        beer = beer,
                    //        modifier = Modifier.fillMaxWidth()
                    //    )
                    //}
                }
                item {
                    if(characters.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}