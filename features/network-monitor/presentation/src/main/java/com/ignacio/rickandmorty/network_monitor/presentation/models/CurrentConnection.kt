package com.ignacio.rickandmorty.network_monitor.presentation.models

data class CurrentConnection(
    val networkConnected: Boolean,
    val previous: Boolean,
)
