package com.ignacio.rickandmorty.kotlin_utils.extensions

fun String.isValidEmail(): Boolean {
    return matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex())
}
