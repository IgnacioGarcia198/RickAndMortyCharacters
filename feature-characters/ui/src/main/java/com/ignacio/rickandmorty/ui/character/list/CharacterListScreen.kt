package com.ignacio.rickandmorty.ui.character.list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ignacio.rickandmorty.presentation.character.list.viewmodel.RMCharactersViewModel
import com.ignacio.rickandmorty.presentation.character.list.viewmodel.RMCharactersViewModelContract
import com.ignacio.rickandmorty.presentation.character.models.UiRMCharacter
import com.ignacio.rickandmorty.resources.R
import com.ignacio.rickandmorty.ui.theme.Pink80
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    viewModel: RMCharactersViewModelContract = hiltViewModel<RMCharactersViewModel>(),
    onCharacterClick: (id: Int) -> Unit = {},
) {
    var showingSearchTextField by rememberSaveable {
        mutableStateOf(false)
    }
    val searchCriteria by viewModel.query.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }

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

    val barContentDesc = stringResource(id = R.string.character_list_title_cd)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (showingSearchTextField) {
                        OutlinedTextField(
                            value = searchCriteria.name,
                            onValueChange = {
                                viewModel.justNameQuery(it)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            placeholder = {
                                Text(
                                    stringResource(R.string.search),
                                    style = TextStyle(fontSize = 22.sp)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    } else {
                        Text(stringResource(id = R.string.character_list_title), modifier = Modifier.semantics { contentDescription = barContentDesc })
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Pink80),
                actions = {
                    if (showingSearchTextField) {
                        IconButton(onClick = {
                            showBottomSheet = true
                        }) {
                            Icon(Icons.Default.Build, contentDescription = stringResource(id = R.string.advanced_search_title))
                        }
                    }
                    IconButton(onClick = {
                        if (showingSearchTextField) {
                            showingSearchTextField = false
                            viewModel.clearQuery()
                        } else {
                            showingSearchTextField = true
                        }
                    }) {
                        Icon(
                            imageVector = if (showingSearchTextField) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = stringResource(id = if (showingSearchTextField) R.string.close else R.string.search)
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val scope = rememberCoroutineScope()

        Box(modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)) {
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
                            RMCharacterItem(
                                character = character,
                                onCharacterClick = onCharacterClick
                            )
                        }
                    }
                    item {
                        if (characters.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    // Sheet content
                    AdvancedSearchBottomSheet(
                        criteria = searchCriteria,
                        updateCriteria = { viewModel.setQuery(it) },
                        onClose = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        })
                }
            }
        }
    }
}