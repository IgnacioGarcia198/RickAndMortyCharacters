package com.ignacio.rickandmorty.data.paging.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ignacio.rickandmorty.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.data.paging.models.TestLocalRMCharacter
import com.ignacio.rickandmorty.data.paging.updater.RemoteLocalUpdater
import com.ignacio.rickandmorty.kotlin_utils.exceptions.TestException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test

@OptIn(ExperimentalPagingApi::class)
class RMCharactersMediatorTest {
    private val criteria = CharacterQueryCriteria.default
    private val updater: RemoteLocalUpdater<CharacterQueryCriteria, Int> = mockk {
        coEvery { updateFromRemote(any(), any(), any()) }.returns(Result.success(false))
    }
    private val mediatorState: PagingState<Int, LocalRMCharacter> = mockk {
        every { lastItemOrNull() }.returns(TestLocalRMCharacter.dummy)
    }

    private val mediator = RMCharactersMediator(queryCriteria = criteria, updater = updater)

    @Test
    fun `load() uses RemoteLocalUpdater to update data from remote, tells it to clear local cache with first page if load type is Refresh`() =
        runBlocking {
            val result = mediator.load(LoadType.REFRESH, mediatorState)

            coVerify { updater.updateFromRemote(criteria, 1, true) }


            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @Test
    fun `load() does not make RemoteLocalUpdater clear local cache if load type is Append`() =
        runBlocking {
            val result = mediator.load(LoadType.APPEND, mediatorState)

            coVerify { updater.updateFromRemote(criteria, 1, false) }


            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @Test
    fun `load() does not call RemoteLocalUpdater if load type is Prepend`() = runBlocking {
        val result = mediator.load(LoadType.PREPEND, mediatorState)

        coVerify(exactly = 0) { updater.updateFromRemote(criteria, 1, false) }


        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load() calls RemoteLocalUpdater with first page if load type is Append and there was no last item`() =
        runBlocking {
            every { mediatorState.lastItemOrNull() }.returns(null)

            val result = mediator.load(LoadType.APPEND, mediatorState)

            coVerify { updater.updateFromRemote(criteria, 1, false) }


            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @Test
    fun `load() calls RemoteLocalUpdater with next page key if load type is Append and there was next item`() =
        runBlocking {
            every { mediatorState.lastItemOrNull() }.returns(TestLocalRMCharacter.dummy.copy(id = 40))

            val result = mediator.load(LoadType.APPEND, mediatorState)

            coVerify { updater.updateFromRemote(criteria, 3, false) }


            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @Test
    fun `load() returns true if RemoteLocalUpdater returns true`() = runBlocking {
        coEvery { updater.updateFromRemote(any(), any(), any()) }.returns(Result.success(true))

        val result = mediator.load(LoadType.APPEND, mediatorState)

        coVerify { updater.updateFromRemote(criteria, 1, false) }


        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load() returns error if RemoteLocalUpdater returns error`() = runBlocking {
        coEvery { updater.updateFromRemote(any(), any(), any()) }.returns(
            Result.failure(
                TestException()
            )
        )

        val result = mediator.load(LoadType.APPEND, mediatorState)

        coVerify { updater.updateFromRemote(criteria, 1, false) }


        assertTrue(result is RemoteMediator.MediatorResult.Error)
        assertEquals(TestException(), (result as RemoteMediator.MediatorResult.Error).throwable)
    }
}
