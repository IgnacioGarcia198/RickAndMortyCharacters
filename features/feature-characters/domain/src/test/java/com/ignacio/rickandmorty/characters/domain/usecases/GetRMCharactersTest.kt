package com.ignacio.rickandmorty.characters.domain.usecases

import com.ignacio.rickandmorty.characters.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.characters.domain.models.RMCharacter
import com.ignacio.rickandmorty.characters.domain.repository.RMCharactersPagingRepository
import com.ignacio.rickandmorty.characters.domain.usecases.GetRMCharacters
import com.ignacio.rickandmorty.kotlin_utils.paging.PagedData
import com.ignacio.rickandmorty.kotlin_utils.paging.TestPagedData
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

class GetRMCharactersTest {
    private val repository: RMCharactersPagingRepository = mockk()
    private val getRMCharacters = GetRMCharacters(repository)

    private val character = RMCharacter.dummy.copy(name = "hello")

    private val repositoryFlow: MutableStateFlow<PagedData<RMCharacter>> =
        MutableStateFlow(TestPagedData(character))

    @Before
    fun setUp() {
        every { repository.getRMCharacters(any()) }.returns(repositoryFlow)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GetRMCharacters uses repository to get paged data`() = runTest {
        val data = getRMCharacters(CharacterListQueryCriteria.default).first()

        advanceUntilIdle()

        verify { repository.getRMCharacters(query = CharacterListQueryCriteria.default) }
        TestCase.assertEquals(TestPagedData(character), data)
    }
}
