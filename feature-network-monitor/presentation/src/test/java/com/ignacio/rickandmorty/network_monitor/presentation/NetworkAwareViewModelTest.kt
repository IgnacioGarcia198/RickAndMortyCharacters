package com.ignacio.rickandmorty.network_monitor.presentation

import com.ignacio.rickandmorty.kotlin_utils.coroutines.CoroutineTestRule
import com.ignacio.rickandmorty.network_monitor.domain.models.CurrentConnection
import com.ignacio.rickandmorty.network_monitor.domain.usecases.IsNetworkConnected
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.ignacio.rickandmorty.network_monitor.presentation.models.CurrentConnection as PresentationConnection

class NetworkAwareViewModelTest {
    @get: Rule
    val coroutineTestRule =
        CoroutineTestRule()

    private val useCaseFlow =
        MutableStateFlow(CurrentConnection(networkConnected = true, previous = true))
    private val isNetworkConnected: IsNetworkConnected = mockk()
    private lateinit var viewModel: NetworkAwareViewModelContract
    private val receivedUiStates = mutableListOf<PresentationConnection>()


    @Before
    fun setUp() {
        receivedUiStates.clear()
        every { isNetworkConnected() }.returns(useCaseFlow)
        viewModel = NetworkAwareViewModel(isNetworkConnected)
        observeViewModel(viewModel)
    }

    @Test
    fun `initial state is true, true`() {
        assertObservedStates(PresentationConnection(networkConnected = true, previous = true))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `ViewModel uses IsNetworkConnected to get current and last connection info`() = runTest {
        useCaseFlow.value = CurrentConnection(networkConnected = false, previous = false)
        advanceUntilIdle()

        assertObservedStates(
            PresentationConnection(networkConnected = true, previous = true),
            PresentationConnection(networkConnected = false, previous = false),
        )
    }

    private fun assertObservedStates(vararg states: PresentationConnection) {
        TestCase.assertEquals(states.toList(), receivedUiStates)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeViewModel(viewModel: NetworkAwareViewModelContract) {
        CoroutineScope(UnconfinedTestDispatcher()).launch {
            viewModel.isNetworkConnectedFlow.collect {
                println("######## RECEIVED STATE: $it")
                receivedUiStates.add(it)
            }
        }
    }
}
