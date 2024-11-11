package com.ignacio.rickandmorty.ui.character.detail

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ignacio.rickandmorty.presentation.character.detail.RMCharacterDetailViewModel
import com.ignacio.rickandmorty.presentation.character.detail.RMCharacterDetailViewModelContract
import com.ignacio.rickandmorty.presentation.character.models.RMCharacterDetailState
import com.ignacio.rickandmorty.resources.R

@Composable
fun CharacterDetailScreen(
    id: Int,
    viewModel: RMCharacterDetailViewModelContract = hiltViewModel<RMCharacterDetailViewModel, RMCharacterDetailViewModel.ViewModelFactory> { factory ->
        factory.create(id)
    }
) {
    when (val state = viewModel.state) {
        RMCharacterDetailState.CharacterNotFound -> Text(text = stringResource(id = R.string.character_not_found))
        is RMCharacterDetailState.Data -> Text(text = "${state.character}")
        is RMCharacterDetailState.Error -> Text(text = state.error?.stackTraceToString().orEmpty())
        RMCharacterDetailState.Loading -> CircularProgressIndicator()
    }
}
