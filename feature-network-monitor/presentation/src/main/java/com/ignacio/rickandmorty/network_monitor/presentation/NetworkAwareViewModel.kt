package com.ignacio.rickandmorty.network_monitor.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignacio.rickandmorty.network_monitor.domain.repository.NetworkAwareRepository
import com.ignacio.rickandmorty.network_monitor.presentation.mapping.toPresentation
import com.ignacio.rickandmorty.network_monitor.presentation.models.CurrentConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NetworkAwareViewModel @Inject constructor(
    repository: NetworkAwareRepository,
) : ViewModel(), NetworkAwareViewModelContract {
    override val isNetworkConnectedFlow: StateFlow<CurrentConnection> =
        repository.isNetworkConnectedFlow.map {
            it.toPresentation()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = CurrentConnection(networkConnected = true, previous = true)
        )
}