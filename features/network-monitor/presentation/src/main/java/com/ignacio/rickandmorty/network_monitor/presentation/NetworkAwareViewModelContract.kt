package com.ignacio.rickandmorty.network_monitor.presentation

import com.ignacio.rickandmorty.network_monitor.presentation.models.CurrentConnection
import kotlinx.coroutines.flow.StateFlow

interface NetworkAwareViewModelContract {
    val isNetworkConnectedFlow: StateFlow<CurrentConnection>
}