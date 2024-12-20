package com.ignacio.rickandmorty.characters.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.ignacio.rickandmorty.kotlin_utils.build_config.BuildConfig
import com.ignacio.rickandmorty.characters.presentation.detail.RMCharacterDetailViewModel
import com.ignacio.rickandmorty.characters.presentation.detail.RMCharacterDetailViewModelContract
import com.ignacio.rickandmorty.characters.presentation.models.RMCharacterDetailState
import com.ignacio.rickandmorty.characters.presentation.models.UiRMCharacter
import com.ignacio.rickandmorty.resources.R
import com.ignacio.rickandmorty.ui_common.theme.AppTheme
import com.ignacio.rickandmorty.ui_common.theme.AppTopBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    id: Int,
    viewModel: RMCharacterDetailViewModelContract = hiltViewModel<RMCharacterDetailViewModel, RMCharacterDetailViewModel.ViewModelFactory> { factory ->
        factory.create(id)
    },
    goBack: () -> Unit = {},
) {
    val state = viewModel.state
    Scaffold(
        topBar = {
            val titleText =
                if (state is RMCharacterDetailState.Data) state.character.name else stringResource(
                    id = R.string.app_name
                )
            val titleContentDesc =
                if (state is RMCharacterDetailState.Data) stringResource(
                    id = R.string.character_detail_title_cd,
                    state.character.name
                ) else stringResource(
                    id = R.string.app_name
                )
            TopAppBar(
                title = {
                    Text(
                        titleText,
                        modifier = Modifier.semantics { contentDescription = titleContentDesc },
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                colors = AppTopBarColors(),
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_back_arrow_content_desc),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when (state) {
                RMCharacterDetailState.CharacterNotFound -> Text(
                    text = stringResource(id = R.string.character_not_found),
                    modifier = Modifier.align(Alignment.Center),
                )

                is RMCharacterDetailState.Data -> DisplayCharacter(state.character)
                is RMCharacterDetailState.Error -> Text(
                    text = if (BuildConfig.DEBUG) state.error?.stackTraceToString()
                        .orEmpty() else stringResource(
                        id = R.string.error_feedback_text,
                        state.error?.message.orEmpty()
                    ),
                    style = TextStyle(color = Color.Red),
                    modifier = Modifier.align(Alignment.Center),
                )

                RMCharacterDetailState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }
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
            contentScale = ContentScale.Fit,
            placeholder = painterResource(id = R.drawable.baseline_image_search_24),
            error = painterResource(id = R.drawable.baseline_broken_image_24),
            fallback = painterResource(id = R.drawable.baseline_hide_image_24),
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
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = attrValue,
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier.weight(1f)
        )
    }
    if (spaceBelow) {
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(
    name = "Character detail screen sample with ViewModel",
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun CharacterDetailScreenPreview(
    @PreviewParameter(SampleCharacterDetailStateProvider::class) state: RMCharacterDetailState,
) {
    class FakeViewModel(
        override val state: RMCharacterDetailState,
    ) : RMCharacterDetailViewModelContract


    val viewModel = FakeViewModel(state = state)
    AppTheme {
        CharacterDetailScreen(id = 1, viewModel = viewModel)
    }
}

class SampleCharacterDetailStateProvider : PreviewParameterProvider<RMCharacterDetailState> {
    override val values: Sequence<RMCharacterDetailState> = sequenceOf(
        RMCharacterDetailState.Loading,
        RMCharacterDetailState.CharacterNotFound,
        RMCharacterDetailState.Error(RuntimeException("something happened")),
        RMCharacterDetailState.Data(character = UiRMCharacter.dummy),
        RMCharacterDetailState.Data(character = UiRMCharacter.dummy.copy(type = "some type")),
    )
}
