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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Spinner(
    selected: String,
    entries: List<String>,
    modifier: Modifier = Modifier,
    label: String = "",
    labelWeight: Float = 0.85f,
    onSelection: (String) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    Row(modifier = modifier) {
        if (label.isNotEmpty()) {
            Text(text = label, modifier = Modifier
                .padding(end = 8.dp)
                .weight(labelWeight))
        }
        Column(modifier = Modifier.weight((1f - labelWeight).coerceAtLeast(0.1f))) {
            Row(
                modifier = Modifier.clickable {
                    expanded = !expanded
                }
            ) {
                Text(text = selected, modifier = Modifier)
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                entries.forEach { option ->
                    val isSelected = option == selected
                    DropdownMenuItem(
                        modifier = if (isSelected) Modifier.background(MaterialTheme.colorScheme.primary) else Modifier,
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
                            Text(option, style = style)
                        }
                    )
                }
            }
        }
    }
}