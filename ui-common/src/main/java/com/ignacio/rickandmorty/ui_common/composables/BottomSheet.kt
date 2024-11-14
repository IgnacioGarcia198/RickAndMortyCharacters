package com.ignacio.rickandmorty.ui_common.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ignacio.rickandmorty.resources.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    show: Boolean,
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    @StringRes closeButton: Int = R.string.close,
    onClose: (() -> Unit)? = null,
    onDismissRequest: () -> Unit = onClose ?: {},
    skipPartiallyExpanded: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (show) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
        val scope = rememberCoroutineScope()

        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    stringResource(id = title),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                content()
                onClose?.let {
                    Button(
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onClose()
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 16.dp, bottom = 16.dp)
                    ) {
                        Text(stringResource(id = closeButton))
                    }
                }
            }
        }
    }
}

@Composable
private fun SheetContent(
    onClose: (() -> Unit)?
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            stringResource(id = R.string.advanced_search_title),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
    }
}