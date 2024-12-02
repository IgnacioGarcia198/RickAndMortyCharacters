package com.ignacio.rickandmorty.kotlin_utils.ui

data class UiField<T>(
    val value: T,
    val inputErrors: List<String> = emptyList(),
) {
    val hasError: Boolean get() = inputErrors.isNotEmpty()
}

val UiField<String>.isValid: Boolean get() = !hasError && value.isNotBlank()
