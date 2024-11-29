package com.ignacio.rickandmorty.network_monitor.data.repository

import com.ignacio.rickandmorty.network.ConnectivityMonitor
import com.ignacio.rickandmorty.network_monitor.domain.models.CurrentConnection
import com.ignacio.rickandmorty.network_monitor.domain.repository.NetworkAwareRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RealNetworkAwareRepository @Inject constructor(
    connectivityMonitor: ConnectivityMonitor
) : NetworkAwareRepository {
    private var lastConnected = true

    override val isNetworkConnectedFlow: Flow<CurrentConnection> =
        connectivityMonitor.isNetworkConnectedFlow.map { newConnected ->
            CurrentConnection(newConnected, lastConnected).also {
                lastConnected = newConnected
            }
        }
}
