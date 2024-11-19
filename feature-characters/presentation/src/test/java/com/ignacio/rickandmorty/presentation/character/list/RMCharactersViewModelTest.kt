package com.ignacio.rickandmorty.presentation.character.list

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.domain.models.RMCharacter
import com.ignacio.rickandmorty.domain.usecases.GetRMCharacters
import com.ignacio.rickandmorty.kotlin_utils.coroutines.CoroutineTestRule
import com.ignacio.rickandmorty.kotlin_utils.paging.PagedData
import com.ignacio.rickandmorty.paging.models.AndroidPagedData
import com.ignacio.rickandmorty.presentation.character.list.viewmodel.RMCharactersViewModel
import com.ignacio.rickandmorty.presentation.character.list.viewmodel.RMCharactersViewModelContract
import com.ignacio.rickandmorty.presentation.character.mapping.toUi
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
class RMCharactersViewModelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: RMCharactersViewModelContract
    private val getRMCharacters: GetRMCharacters = mockk()

    private val character = RMCharacter.dummy.copy(name = "hello")
    private val pagingData: PagingData<RMCharacter> = PagingData.from(
        data = listOf(character),
        sourceLoadStates = LoadStates(
            refresh = LoadState.NotLoading(endOfPaginationReached = false),
            append = LoadState.NotLoading(endOfPaginationReached = false),
            prepend = LoadState.NotLoading(endOfPaginationReached = false),
        ),
    )

    private val useCaseFlow: MutableStateFlow<PagedData<RMCharacter>> =
        MutableStateFlow(AndroidPagedData(pagingData = pagingData))
    private val query = CharacterListQueryCriteria.default

    @Before
    fun setUp() {
        every { getRMCharacters(any()) }.returns(useCaseFlow)
        viewModel = RMCharactersViewModel(getRMCharacters)
    }

    @Test
    fun `ViewModel uses data source to get paged data`() = runTest {
        val characters = viewModel.pagingDataFlow.asSnapshot()

        advanceUntilIdle()

        verify { getRMCharacters(query = CharacterListQueryCriteria.default) }
        assertEquals(listOf(character.toUi()), characters)
    }

    @Test
    fun `updateName() sets the name for use case call`() = runTest {
        viewModel.updateName("new name")
        viewModel.pagingDataFlow.asSnapshot()
        advanceUntilIdle()


        verify { getRMCharacters(query = query.copy(name = "new name")) }
    }

    @Test
    fun `updateSpecies() sets the species for use case call`() = runTest {
        viewModel.updateSpecies("new species")
        viewModel.pagingDataFlow.asSnapshot()
        advanceUntilIdle()


        verify { getRMCharacters(query = query.copy(species = "new species")) }
    }

    @Test
    fun `updateType() sets the type for use case call`() = runTest {
        viewModel.updateType("new type")
        viewModel.pagingDataFlow.asSnapshot()
        advanceUntilIdle()


        verify { getRMCharacters(query = query.copy(type = "new type")) }
    }

    @Test
    fun `updateStatus() sets the status for use case call`() = runTest {
        viewModel.updateStatus(CharacterListQueryCriteria.Status.ALIVE)
        viewModel.pagingDataFlow.asSnapshot()
        advanceUntilIdle()


        verify { getRMCharacters(query = query.copy(status = CharacterListQueryCriteria.Status.ALIVE)) }
    }

    @Test
    fun `updateGender() sets the gender for use case call`() = runTest {
        viewModel.updateGender(CharacterListQueryCriteria.Gender.FEMALE)
        viewModel.pagingDataFlow.asSnapshot()
        advanceUntilIdle()


        verify { getRMCharacters(query = query.copy(gender = CharacterListQueryCriteria.Gender.FEMALE)) }
    }

    @Test
    fun `justNameQuery() sets the query for use case call`() = runTest {
        viewModel.justNameQuery("hello")
        viewModel.pagingDataFlow.asSnapshot()
        advanceUntilIdle()


        verify { getRMCharacters(query = query.copy(name = "hello")) }
    }

    @Test
    fun `clearQuery() sets the default query for use case call`() = runTest {
        viewModel.clearQuery()
        viewModel.pagingDataFlow.asSnapshot()
        advanceUntilIdle()


        verify { getRMCharacters(query = CharacterListQueryCriteria.default) }
    }
}
