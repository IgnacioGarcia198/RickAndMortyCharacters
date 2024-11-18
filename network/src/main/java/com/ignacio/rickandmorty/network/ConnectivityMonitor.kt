package com.ignacio.rickandmorty.network

import kotlinx.coroutines.flow.StateFlow

interface ConnectivityMonitor {
    val isNetworkConnectedFlow: StateFlow<Boolean>

    val isNetworkConnected: Boolean

    fun startMonitoringNetworkConnection()

    fun stopMonitoringNetworkConnection()
}
