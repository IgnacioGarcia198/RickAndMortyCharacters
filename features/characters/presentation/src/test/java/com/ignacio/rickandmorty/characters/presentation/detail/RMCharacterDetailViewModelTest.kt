package com.ignacio.rickandmorty.characters.presentation.detail

import com.ignacio.rickandmorty.characters.domain.models.RMCharacter
import com.ignacio.rickandmorty.characters.domain.usecases.GetRMCharacterDetail
import com.ignacio.rickandmorty.characters.presentation.detail.RMCharacterDetailViewModel
import com.ignacio.rickandmorty.characters.presentation.detail.RMCharacterDetailViewModelContract
import com.ignacio.rickandmorty.kotlin_utils.coroutines.CoroutineTestRule
import com.ignacio.rickandmorty.kotlin_utils.exceptions.TestException
import com.ignacio.rickandmorty.characters.presentation.mapping.toUi
import com.ignacio.rickandmorty.characters.presentation.models.RMCharacterDetailState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RMCharacterDetailViewModelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: RMCharacterDetailViewModelContract
    private val getRMCharacterDetail: GetRMCharacterDetail = mockk {}
    private val id = 1

    private val getRMCharacterDetailFlow: MutableStateFlow<Result<RMCharacter?>> =
        MutableStateFlow(Result.success(RMCharacter.dummy))

    @Before
    fun setUp() {
        every { getRMCharacterDetail(any()) }.returns(getRMCharacterDetailFlow)
        viewModel = RMCharacterDetailViewModel(getRMCharacterDetail = getRMCharacterDetail, id = id)
    }

    @Test
    fun `initial state`() {
        assertEquals(RMCharacterDetailState.Loading, viewModel.state)
    }

    @Test
    fun `ViewModel calls GetRMCharacterDetail on startup`() = runTest {
        advanceUntilIdle()

        verify { getRMCharacterDetail(id) }
    }

    @Test
    fun `ViewModel uses GetRMCharacterDetail to update its state`() = runTest {
        advanceUntilIdle()
        verify { getRMCharacterDetail(id) }

        getRMCharacterDetailFlow.value = Result.success(RMCharacter.dummy.copy(name = "hello"))
        advanceUntilIdle()

        assertEquals(
            RMCharacterDetailState.Data(RMCharacter.dummy.copy(name = "hello").toUi()),
            viewModel.state
        )
    }

    @Test
    fun `ViewModel sets state to error if GetRMCharacterDetail call fails`() = runTest {
        advanceUntilIdle()
        verify { getRMCharacterDetail(id) }

        getRMCharacterDetailFlow.value = Result.failure(TestException())
        advanceUntilIdle()

        assertEquals(
            RMCharacterDetailState.Error(TestException()),
            viewModel.state
        )
    }

    @Test
    fun `ViewModel sets state to not found if GetRMCharacterDetail call returns null`() = runTest {
        advanceUntilIdle()
        verify { getRMCharacterDetail(id) }

        getRMCharacterDetailFlow.value = Result.success(null)
        advanceUntilIdle()

        assertEquals(
            RMCharacterDetailState.CharacterNotFound,
            viewModel.state
        )
    }
}