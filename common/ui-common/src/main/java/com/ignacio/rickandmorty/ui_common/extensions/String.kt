package com.ignacio.rickandmorty.ui_common.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ignacio.rickandmorty.resources.R

@Composable
fun String.blankExplicit(): String =
    ifBlank { stringResource(id = R.string.explicit_blank_text) }