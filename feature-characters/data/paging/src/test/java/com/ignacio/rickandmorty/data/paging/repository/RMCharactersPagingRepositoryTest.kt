package com.ignacio.rickandmorty.data.paging.repository

import com.ignacio.rickandmorty.data.paging.datasource.local.CharactersLocalPagingDataSource
import com.ignacio.rickandmorty.data.paging.mapping.toData
import com.ignacio.rickandmorty.data.paging.mapping.toDomain
import com.ignacio.rickandmorty.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.data.paging.models.TestLocalRMCharacter
import com.ignacio.rickandmorty.data.paging.updater.RemoteLocalUpdater
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.domain.repository.RMCharactersPagingRepository
import com.ignacio.rickandmorty.kotlin_utils.paging.TestPagedData
import com.ignacio.rickandmorty.kotlin_utils.paging.TestPager
import com.ignacio.rickandmorty.paging.pager.PagerFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RMCharactersPagingRepositoryTest {
    private val localDataSource: CharactersLocalPagingDataSource = mockk()
    private val pagerFactory: PagerFactory<Int, LocalRMCharacter> = mockk()
    private val remoteLocalUpdater: RemoteLocalUpdater = mockk()
    private val repository: RMCharactersPagingRepository = RealRMCharactersPagingRepository(
        charactersLocalDataSource = localDataSource,
        pagerFactory = pagerFactory,
        remoteLocalUpdater = remoteLocalUpdater,
    )
    private val query = CharacterListQueryCriteria.default
    private val dataQuery = query.toData()
    private val testPagerFlow: Flow<TestPagedData<LocalRMCharacter>> = MutableStateFlow(
        TestPagedData(TestLocalRMCharacter.dummy)
    )
    private val testPager = TestPager<Int, LocalRMCharacter>(testPagerFlow)
    private val key = 1


    @Before
    fun setUp() {
        val updateCallbackSlot =
            slot<suspend (page: Int, shouldClearLocalCache: Boolean) -> Result<Boolean>>()
        every { pagerFactory.create(capture(updateCallbackSlot), any()) }.coAnswers {
            updateCallbackSlot.captured.invoke(key, false)
            testPager
        }
        coEvery { remoteLocalUpdater.updateFromRemote(any(), any(), any()) }.returns(
            Result.success(
                false
            )
        )
    }

    @Test
    fun `getRMCharacters() uses PagerFactory to create a new Pager`() = runTest {
        val data = repository.getRMCharacters(query).first()
        advanceUntilIdle()

        verify { pagerFactory.create(any(), any()) }
        assertEquals(TestPagedData(TestLocalRMCharacter.dummy.toDomain()), data)
    }

    @Test
    fun `getRMCharacters() uses RemoteLocalUpdater to update local data`() = runTest {
        val data = repository.getRMCharacters(query).first()
        advanceUntilIdle()

        verify { pagerFactory.create(any(), any()) }
        coVerify {
            remoteLocalUpdater.updateFromRemote(
                query = dataQuery,
                page = key,
                shouldClearLocalCache = false
            )
        }
        assertEquals(TestPagedData(TestLocalRMCharacter.dummy.toDomain()), data)
    }

    @Test
    fun `getRMCharacters() tells RemoteLocalUpdater to clear local cache if mediator call says so`() =
        runTest {
            val updateCallbackSlot =
                slot<suspend (page: Int, shouldClearLocalCache: Boolean) -> Result<Boolean>>()
            every { pagerFactory.create(capture(updateCallbackSlot), any()) }.coAnswers {
                updateCallbackSlot.captured.invoke(key, true)
                testPager
            }

            val data = repository.getRMCharacters(query).first()
            advanceUntilIdle()

            verify { pagerFactory.create(any(), any()) }
            coVerify {
                remoteLocalUpdater.updateFromRemote(
                    query = dataQuery,
                    page = key,
                    shouldClearLocalCache = true
                )
            }
            assertEquals(TestPagedData(TestLocalRMCharacter.dummy.toDomain()), data)
        }
}
