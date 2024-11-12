package com.ignacio.rickandmorty.ui.character.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.ignacio.rickandmorty.presentation.character.detail.RMCharacterDetailViewModel
import com.ignacio.rickandmorty.presentation.character.detail.RMCharacterDetailViewModelContract
import com.ignacio.rickandmorty.presentation.character.models.RMCharacterDetailState
import com.ignacio.rickandmorty.presentation.character.models.UiRMCharacter
import com.ignacio.rickandmorty.resources.R

@Composable
fun CharacterDetailScreen(
    id: Int,
    viewModel: RMCharacterDetailViewModelContract = hiltViewModel<RMCharacterDetailViewModel, RMCharacterDetailViewModel.ViewModelFactory> { factory ->
        factory.create(id)
    }
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (val state = viewModel.state) {
            RMCharacterDetailState.CharacterNotFound -> Text(
                text = stringResource(id = R.string.character_not_found),
                modifier = Modifier.align(Alignment.Center),
            )

            is RMCharacterDetailState.Data -> DisplayCharacter(state.character)
            is RMCharacterDetailState.Error -> Text(
                text = state.error?.stackTraceToString().orEmpty(),
                style = TextStyle(color = Color.Red),
                modifier = Modifier.align(Alignment.Center),
            )

            RMCharacterDetailState.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Composable
private fun DisplayCharacter(character: UiRMCharacter) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = character.name,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(32.dp))
        AttributeTable(character = character)
    }
}

@Composable
private fun AttributeTable(character: UiRMCharacter) {
    AttributeValueRow(
        attrName = stringResource(id = R.string.character_detail_species),
        attrValue = character.species
    )
    AttributeValueRow(
        attrName = stringResource(id = R.string.character_detail_status),
        attrValue = character.status
    )
    AttributeValueRow(
        attrName = stringResource(id = R.string.character_detail_gender),
        attrValue = character.gender
    )
    if (!character.type.isNullOrBlank()) {
        AttributeValueRow(
            attrName = stringResource(id = R.string.character_detail_type),
            attrValue = character.type!!
        )
    }
    AttributeValueRow(
        attrName = stringResource(id = R.string.character_detail_created),
        attrValue = character.created,
        spaceBelow = false,
    )
}

@Composable
private fun AttributeValueRow(
    attrName: String,
    attrValue: String,
    modifier: Modifier = Modifier,
    spaceBelow: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "$attrName:",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
            modifier = Modifier.weight(0.3f) // Occupy equal width
        )
        Text(
            text = attrValue,
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier.weight(1f) // Occupy equal width
        )
    }
    if (spaceBelow) {
        Spacer(modifier = Modifier.height(16.dp))
    }
}
