package com.ignacio.rickandmorty.ui_common.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ignacio.rickandmorty.resources.R

@Composable
fun ErrorBottomSheet(
    errorText: String,
    onClose: () -> Unit = {},
) {
    AppBottomSheet(
        show = errorText.isNotBlank(),
        title = R.string.error_sheet_title,
        onClose = onClose,
    ) {
        Text(
            text = stringResource(id = R.string.error_details_section, errorText),
            style = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}
