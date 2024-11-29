package com.ignacio.rickandmorty.data.repository

import com.ignacio.rickandmorty.data.datasources.local.CharactersLocalDataSource
import com.ignacio.rickandmorty.data.models.RMCharacter
import com.ignacio.rickandmorty.domain.repository.RMCharactersRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.ignacio.rickandmorty.domain.models.RMCharacter as DomainCharacter

@OptIn(ExperimentalCoroutinesApi::class)
class RMCharactersRepositoryTest {
    private val charactersLocalDataSource: CharactersLocalDataSource = mockk()

    private val repository: RMCharactersRepository = RealRMCharactersRepository(
        charactersLocalDataSource,
    )
    private val id = 1
    private val localDataSourceFlow: MutableStateFlow<Result<RMCharacter?>> =
        MutableStateFlow(Result.success(RMCharacter.dummy))

    @Before
    fun setUp() {
        every { charactersLocalDataSource.getRMCharacterById(any()) }.returns(localDataSourceFlow)
    }

    @Test
    fun `repository uses CharactersLocalDataSource to get character by id`() = runTest {
        var result = repository.getRMCharacterById(id).first()
        advanceUntilIdle()

        verify { charactersLocalDataSource.getRMCharacterById(id) }
        TestCase.assertEquals(
            Result.success(DomainCharacter.dummy),
            result
        )


        localDataSourceFlow.value = Result.success(null)
        result = repository.getRMCharacterById(id).first()
        advanceUntilIdle()

        TestCase.assertEquals(Result.success(null), result)
    }
}
