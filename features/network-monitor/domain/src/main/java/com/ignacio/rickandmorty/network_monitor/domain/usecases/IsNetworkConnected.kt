package com.ignacio.rickandmorty.network_monitor.domain.usecases

import com.ignacio.rickandmorty.network_monitor.domain.models.CurrentConnection
import com.ignacio.rickandmorty.network_monitor.domain.repository.NetworkAwareRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsNetworkConnected @Inject constructor(
    private val repository: NetworkAwareRepository,
) {
    operator fun invoke(): Flow<CurrentConnection> = repository.isNetworkConnectedFlow
}