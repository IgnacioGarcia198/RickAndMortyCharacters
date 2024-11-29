package com.ignacio.rickandmorty.network_monitor.domain.usecases

import com.ignacio.rickandmorty.network_monitor.domain.models.CurrentConnection
import com.ignacio.rickandmorty.network_monitor.domain.repository.NetworkAwareRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

class IsNetworkConnectedTest {
    private val repositoryFlow = MutableStateFlow(CurrentConnection(networkConnected = true, previous = true))
    private val repository: NetworkAwareRepository = mockk {
        every { isNetworkConnectedFlow }.returns(repositoryFlow)
    }
    private val isNetworkConnected = IsNetworkConnected(repository)


    @Test
    fun `use case uses Repository to get connectivity status`() = runBlocking {
        val result = isNetworkConnected().first()

        verify { repository.isNetworkConnectedFlow }
        assertEquals(result, repositoryFlow.value)
    }
}
