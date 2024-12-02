package com.ignacio.rickandmorty.ui_common.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.ignacio.rickandmorty.kotlin_utils.ui.UiField

@Composable
fun BasicInputField(
    value: UiField<String>,
    label: @Composable () -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorColor: Color = MaterialTheme.colorScheme.error,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    showErrors: Boolean = true,
) {
    Column {
        OutlinedTextField(
            value = value.value,
            onValueChange = {
                onValueChange(it)
            },
            label = label,
            placeholder = placeholder,
            isError = value.inputErrors.isNotEmpty(),
            trailingIcon = trailingIcon,
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = errorColor,
                errorLabelColor = errorColor,
                errorCursorColor = errorColor,
                errorTextColor = errorColor,
            ),
            modifier = modifier
                .padding(vertical = 8.dp),
        )
        if (showErrors) {
            value.inputErrors.forEach {
                Text(text = it, style = TextStyle(color = errorColor))
            }
        }
    }
}