package com.ignacio.rickandmorty.kotlin_utils.exceptions

data class TestException(override val message: String = "test"): Exception(message)
