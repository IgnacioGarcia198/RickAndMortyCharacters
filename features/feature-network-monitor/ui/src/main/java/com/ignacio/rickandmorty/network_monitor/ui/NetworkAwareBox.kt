package com.ignacio.rickandmorty.network_monitor.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ignacio.rickandmorty.network_monitor.presentation.NetworkAwareViewModel
import com.ignacio.rickandmorty.network_monitor.presentation.NetworkAwareViewModelContract

@Composable
fun NetworkAwareBox(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (SnackbarHostState) -> Unit,
) {
    val viewModel: NetworkAwareViewModelContract = hiltViewModel<NetworkAwareViewModel>()
    val isNetworkAvailable by viewModel.isNetworkConnectedFlow.collectAsState()
    if (!isNetworkAvailable.networkConnected) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                message = "No network connection",
                actionLabel = "Dismiss"
            )
        }
    }
    if (isNetworkAvailable.networkConnected && !isNetworkAvailable.previous) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                message = "Network connection is back",
                duration = SnackbarDuration.Short,
            )
        }
    }
    Box(modifier = modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackbarHostState,
            //modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp) // Position the snackbar
        )
        content(snackbarHostState)
    }
}
