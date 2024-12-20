package com.ignacio.rickandmorty.characters.domain.usecases

import com.ignacio.rickandmorty.characters.domain.models.RMCharacter
import com.ignacio.rickandmorty.characters.domain.repository.RMCharactersRepository
import com.ignacio.rickandmorty.characters.domain.usecases.GetRMCharacterDetail
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
        var result = getRMCharacterDetail(id).first()
        advanceUntilIdle()

        verify { repository.getRMCharacterById(id) }
        assertEquals(Result.success(RMCharacter.dummy), result)


        repositoryFlow.value = Result.success(null)
        result = getRMCharacterDetail(id).first()
        advanceUntilIdle()

        assertEquals(Result.success(null), result)
    }
}