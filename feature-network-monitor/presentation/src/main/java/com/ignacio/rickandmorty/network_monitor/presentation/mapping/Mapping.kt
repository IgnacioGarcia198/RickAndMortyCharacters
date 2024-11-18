package com.ignacio.rickandmorty.network_monitor.presentation.mapping

import com.ignacio.rickandmorty.network_monitor.presentation.models.CurrentConnection


fun com.ignacio.rickandmorty.network_monitor.domain.models.CurrentConnection.toPresentation(): CurrentConnection =
    CurrentConnection(networkConnected, previous)