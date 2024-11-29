package com.ignacio.rickandmorty.network_monitor.domain.repository

import com.ignacio.rickandmorty.network_monitor.domain.models.CurrentConnection
import kotlinx.coroutines.flow.Flow

interface NetworkAwareRepository {
    val isNetworkConnectedFlow: Flow<CurrentConnection>
}