package com.ignacio.rickandmorty.network_monitor.data.repository

import com.ignacio.rickandmorty.network.ConnectivityMonitor
import com.ignacio.rickandmorty.network_monitor.domain.models.CurrentConnection
import com.ignacio.rickandmorty.network_monitor.domain.repository.NetworkAwareRepository
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

class NetworkAwareRepositoryTest {
    private val monitorFlow = MutableStateFlow(true)
    private val connectivityMonitor: ConnectivityMonitor = mockk {
        every { isNetworkConnectedFlow }.returns(monitorFlow)
    }
    private val repository: NetworkAwareRepository = RealNetworkAwareRepository(connectivityMonitor)

    @Test
    fun `Repository's flow initial value is true, true`() = runBlocking {
        val state = repository.isNetworkConnectedFlow.first()

        assertEquals(CurrentConnection(networkConnected = true, previous = true), state)
    }

    @Test
    fun `Repository stores current value in networkConnected and previous value in previous`() = runBlocking {
        monitorFlow.value = false

        val state = repository.isNetworkConnectedFlow.first()

        assertEquals(CurrentConnection(networkConnected = false, previous = true), state)
    }
}
