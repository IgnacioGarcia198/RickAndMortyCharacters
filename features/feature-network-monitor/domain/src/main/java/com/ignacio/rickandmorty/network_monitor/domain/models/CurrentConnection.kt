package com.ignacio.rickandmorty.network_monitor.domain.models

data class CurrentConnection(
    val networkConnected: Boolean,
    val previous: Boolean,
)
