package com.ignacio.rickandmorty.domain.usecases

import com.ignacio.rickandmorty.domain.models.RMCharacter
import com.ignacio.rickandmorty.domain.repository.RMCharactersRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetRMCharacterDetailTest {
    private val repository: RMCharactersRepository = mockk()
    private val getRMCharacterDetail = GetRMCharacterDetail(repository)
    private val id = 1

    private val repositoryFlow: MutableStateFlow<Result<RMCharacter?>> =
        MutableStateFlow(Result.success(RMCharacter.dummy))

    @Before
    fun setUp() {
        every { repository.getRMCharacterById(any()) }.returns(repositoryFlow)
    }

    @Test
    fun `use case uses repository to get character by id`() = runTest {
        val result = getRMCharacterDetail(id).first()
        advanceUntilIdle()

        verify { repository.getRMCharacterById(id) }
        assertEquals(Result.success(RMCharacter.dummy), result)
    }
}