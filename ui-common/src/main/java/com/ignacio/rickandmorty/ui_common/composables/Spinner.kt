package com.ignacio.rickandmorty.ui_common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun <T: Any> Spinner(
    selected: T,
    entries: List<T>,
    modifier: Modifier = Modifier,
    label: String = "",
    labelWeight: Float = 0.75f,
    stringRepresentation: (T) -> String = { it.toString() },
    onSelection: (T) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .clickable {
                expanded = !expanded
            }
    ) {
        if (label.isNotEmpty()) {
            Text(text = label, style = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold), modifier = Modifier
                .padding(end = 8.dp)
                .weight(labelWeight))
        }
        Column(modifier = Modifier) {
            Row {
                Text(text = stringRepresentation(selected), modifier = Modifier)
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                entries.forEach { option ->
                    val isSelected = option == selected
                    val modifier = if (isSelected) Modifier.background(MaterialTheme.colorScheme.primary) else Modifier
                    DropdownMenuItem(
                        modifier = modifier.semantics { contentDescription = "${stringRepresentation(option)}BLA" },
                        onClick = {
                            onSelection(option)
                            expanded = false
                        }, text = {
                            val style = if (isSelected) {
                                TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                LocalTextStyle.current
                            }
                            Text(stringRepresentation(option), style = style)
                        },
                    )
                }
            }
        }
    }
}