package com.ignacio.rickandmorty.characters.ui.list

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ignacio.rickandmorty.characters.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.characters.presentation.list.viewmodel.RMCharactersViewModel
import com.ignacio.rickandmorty.characters.presentation.list.viewmodel.RMCharactersViewModelContract
import com.ignacio.rickandmorty.characters.presentation.models.UiRMCharacter
import com.ignacio.rickandmorty.kotlin_utils.extensions.getDebugOrProductionText
import com.ignacio.rickandmorty.resources.R
import com.ignacio.rickandmorty.ui_common.composables.ErrorBottomSheet
import com.ignacio.rickandmorty.ui_common.composables.SnackbarScaffold
import com.ignacio.rickandmorty.ui_common.theme.AppTheme
import com.ignacio.rickandmorty.ui_common.theme.AppTopBarColors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: RMCharactersViewModelContract = hiltViewModel<RMCharactersViewModel>(),
    onCharacterClick: (id: Int) -> Unit = {},
) {
    var showingSearchTextField by rememberSaveable {
        mutableStateOf(false)
    }
    val searchCriteria by viewModel.query.collectAsState()
    var showAdvancedSearchBottomSheet by rememberSaveable { mutableStateOf(false) }
    var bottomSheetError: String by rememberSaveable { mutableStateOf("") }

    val characters: LazyPagingItems<UiRMCharacter> =
        viewModel.pagingDataFlow.collectAsLazyPagingItems()

    val isRefreshing by remember {
        derivedStateOf {
            characters.loadState.refresh is LoadState.Loading
        }
    }

    LaunchedEffect(key1 = characters.loadState) { // TODO: Move to the backend with some log report files.
        if (characters.loadState.hasError) {
            bottomSheetError = characters.loadState.getErrorText()
        }
    }

    val barContentDesc = stringResource(id = R.string.character_list_title_cd)

    SnackbarScaffold(
        snackbarHostState = snackbarHostState,
        topBar = {
            CharacterListScreenTopBar(
                showingSearchTextField = showingSearchTextField,
                searchCriteria = searchCriteria,
                barContentDescription = barContentDesc,
                onAdvancedSearchClick = {
                    showAdvancedSearchBottomSheet = true
                },
                onSearchClick = {
                    if (showingSearchTextField) {
                        showingSearchTextField = false
                        viewModel.clearQuery()
                    } else {
                        showingSearchTextField = true
                    }
                },
                onSearchTextChange = { viewModel.justNameQuery(it) }
            )
        },
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(all = 8.dp),
            onRefresh = {
                characters.refresh()
            }
        ) {
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
                    val appendState = characters.loadState.append
                    if (appendState is LoadState.Loading) {
                        CircularProgressIndicator()
                    } else if (appendState is LoadState.Error) {
                        Text("Error loading more data: ${appendState.error.message}")
                    }
                }
            }
        }

        AdvancedSearchBottomSheet(
            show = showAdvancedSearchBottomSheet,
            criteria = searchCriteria,
            updateName = viewModel::updateName,
            updateSpecies = viewModel::updateSpecies,
            updateType = viewModel::updateType,
            updateStatus = viewModel::updateStatus,
            updateGender = viewModel::updateGender,
            onClose = {
                showAdvancedSearchBottomSheet = false
            }
        )

        ErrorBottomSheet(
            errorText = bottomSheetError,
            onClose = {
                bottomSheetError = ""
            }
        )
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CharacterListScreenTopBar(
    showingSearchTextField: Boolean,
    searchCriteria: CharacterListQueryCriteria,
    barContentDescription: String,
    onAdvancedSearchClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSearchTextChange: (String) -> Unit,
) {
    TopAppBar(
        title = {
            if (showingSearchTextField) {
                OutlinedTextField(
                    value = searchCriteria.name,
                    onValueChange = onSearchTextChange,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    placeholder = {
                        Text(
                            stringResource(R.string.search),
                            style = LocalTextStyle.current.copy(
                                fontSize = 22.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                )
            } else {
                Text(
                    stringResource(id = R.string.character_list_title),
                    modifier = Modifier.semantics { contentDescription = barContentDescription })
            }
        },
        colors = AppTopBarColors(),
        actions = {
            if (showingSearchTextField) {
                IconButton(onClick = onAdvancedSearchClick) {
                    Icon(
                        Icons.Default.Build,
                        contentDescription = stringResource(id = R.string.advanced_search_title),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = if (showingSearchTextField) Icons.Default.Close else Icons.Default.Search,
                    contentDescription = stringResource(id = if (showingSearchTextField) R.string.close else R.string.search),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    )
}

private fun CombinedLoadStates.getErrorText(): String {
    val sb = StringBuilder()
    sb.appendError(refresh)
    sb.appendError(append)
    sb.appendError(prepend)
    sb.appendError(source.refresh)
    sb.appendError(source.append)
    sb.appendError(source.prepend)
    return sb.toString()
}

private fun StringBuilder.appendError(loadState: LoadState) {
    if (loadState is LoadState.Error) {
        append(loadState.error.getDebugOrProductionText())
        append("\n")
    }
}

@Preview(
    name = "Character list screen sample with ViewModel",
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun CharacterListScreenPreview(
    @PreviewParameter(SampleCharacterPagingDataProvider::class) pagingData: PagingData<UiRMCharacter>,
) {
    class FakeViewModel(
        val queryFlow: MutableStateFlow<CharacterListQueryCriteria> = MutableStateFlow(
            CharacterListQueryCriteria.default
        ),
        override val pagingDataFlow: Flow<PagingData<UiRMCharacter>> = MutableStateFlow(
            PagingData.from(
                data = listOf(UiRMCharacter.dummy)
            )
        ),
    ) : RMCharactersViewModelContract {

        override val query: StateFlow<CharacterListQueryCriteria> = queryFlow

        override fun justNameQuery(name: String) {
            queryFlow.value = CharacterListQueryCriteria.default.copy(name = name)
        }

        override fun updateName(name: String) {

        }

        override fun updateType(type: String) {

        }

        override fun updateSpecies(species: String) {

        }

        override fun updateStatus(status: CharacterListQueryCriteria.Status) {

        }

        override fun updateGender(gender: CharacterListQueryCriteria.Gender) {

        }

        override fun clearQuery() {
            queryFlow.value = CharacterListQueryCriteria.default
        }
    }

    val queryFlow: MutableStateFlow<CharacterListQueryCriteria> =
        MutableStateFlow(CharacterListQueryCriteria.default)
    val pagingDataFlow: Flow<PagingData<UiRMCharacter>> = MutableStateFlow(pagingData)
    val viewModel = FakeViewModel(queryFlow = queryFlow, pagingDataFlow = pagingDataFlow)
    AppTheme {
        CharacterListScreen(
            snackbarHostState = remember {
                SnackbarHostState()
            },
            viewModel = viewModel
        )
    }
}

class SampleCharacterPagingDataProvider : PreviewParameterProvider<PagingData<UiRMCharacter>> {
    override val values = sequenceOf(
        PagingData.from(
            data = listOf(
                UiRMCharacter.dummy,
                UiRMCharacter.dummy.copy(type = "type"),
                UiRMCharacter.dummy.copy(name = "asfjslfsljflajsfljsflsflafjlasfldflkjlsfjlsfalksfklsfdjkldfl"),
            )
        ),
        PagingData.from(
            data = listOf(UiRMCharacter.dummy),
            sourceLoadStates = LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.Loading,
                append = LoadState.Loading,
            ),
            mediatorLoadStates = LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.Loading,
                append = LoadState.Loading,
            ),
        ),
        PagingData.from(
            data = listOf(UiRMCharacter.dummy),
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(false),
                prepend = LoadState.Loading,
                append = LoadState.NotLoading(false),
            ),
            mediatorLoadStates = LoadStates(
                refresh = LoadState.NotLoading(false),
                prepend = LoadState.Loading,
                append = LoadState.NotLoading(false),
            ),
        ),
        PagingData.from(
            data = listOf(UiRMCharacter.dummy),
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(false),
                prepend = LoadState.NotLoading(false),
                append = LoadState.Loading,
            ),
            mediatorLoadStates = LoadStates(
                refresh = LoadState.NotLoading(false),
                prepend = LoadState.NotLoading(false),
                append = LoadState.Loading,
            ),
        ),
        PagingData.from(
            data = listOf<UiRMCharacter>(),
            sourceLoadStates = LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false),
            ),
            mediatorLoadStates = LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false),
            ),
        ),
        PagingData.from(
            data = listOf(UiRMCharacter.dummy),
            sourceLoadStates = LoadStates(
                refresh = LoadState.Error(RuntimeException()),
                prepend = LoadState.Error(RuntimeException()),
                append = LoadState.Error(RuntimeException()),
            ),
            mediatorLoadStates = LoadStates(
                refresh = LoadState.Error(RuntimeException()),
                prepend = LoadState.Error(RuntimeException()),
                append = LoadState.Error(RuntimeException()),
            ),
        ),
    )
}
