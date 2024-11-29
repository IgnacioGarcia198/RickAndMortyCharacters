package com.ignacio.rickandmorty.characters.data.paging.updater

import com.ignacio.rickandmorty.characters.data.models.RMCharacter
import com.ignacio.rickandmorty.characters.data.paging.datasource.local.CharactersLocalPagingDataSource
import com.ignacio.rickandmorty.characters.data.paging.datasource.remote.RickAndMortyApi
import com.ignacio.rickandmorty.characters.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.characters.data.paging.models.RMCharacters
import com.ignacio.rickandmorty.characters.data.paging.updater.RealRemoteLocalUpdater
import com.ignacio.rickandmorty.characters.data.paging.updater.RemoteLocalUpdater
import com.ignacio.rickandmorty.kotlin_utils.exceptions.TestException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoteLocalUpdaterTest {
    private val rickAndMortyApi: RickAndMortyApi = mockk()
    private val localDataSource: CharactersLocalPagingDataSource = mockk()
    private val updater: RemoteLocalUpdater<CharacterQueryCriteria, Int> = RealRemoteLocalUpdater(
        rickAndMortyApi = rickAndMortyApi,
        charactersLocalDataSource = localDataSource,
    )

    private val query = CharacterQueryCriteria.default
    private val key = 1
    private val responseCharacters = listOf(RMCharacter.dummy.copy(name = "hello"))

    @Before
    fun setUp() {
        coEvery { rickAndMortyApi.getCharacters(any(), any()) }.returns(
            Result.success(
                RMCharacters.Characters(
                    characters = responseCharacters,
                    hasNextPage = true,
                    hasPreviousPage = true
                )
            )
        )
        coEvery { localDataSource.upsertAll(any(), any(), any()) }.just(runs)
    }

    @Test
    fun `updateFromRemote() uses remote service to request data and local datasource to store it`() =
        runTest {
            val result =
                updater.updateFromRemote(query = query, page = key, shouldClearLocalCache = false)

            coVerify { rickAndMortyApi.getCharacters(page = key, query = query) }
            coVerify { localDataSource.upsertAll(responseCharacters, clear = false, query = query) }
            TestCase.assertEquals(Result.success(false), result)
        }

    @Test
    fun `updateFromRemote() returns Result_success(true) if remote data has no next page`() =
        runTest {
            coEvery { rickAndMortyApi.getCharacters(any(), any()) }.returns(
                Result.success(
                    RMCharacters.Characters(
                        characters = responseCharacters,
                        hasNextPage = false,
                        hasPreviousPage = true
                    )
                )
            )

            val result =
                updater.updateFromRemote(query = query, page = key, shouldClearLocalCache = false)


            TestCase.assertEquals(Result.success(true), result)
        }

    @Test
    fun `updateFromRemote() tells local datasource to clear local data if shouldClearLocalCache is true`() =
        runTest {
            val result =
                updater.updateFromRemote(query = query, page = key, shouldClearLocalCache = true)

            coVerify { rickAndMortyApi.getCharacters(page = key, query = query) }
            coVerify { localDataSource.upsertAll(responseCharacters, clear = true, query = query) }
            TestCase.assertEquals(Result.success(false), result)
        }

    @Test
    fun `updateFromRemote() returns Result_failure if remote fetch fails`() = runTest {
        coEvery {
            rickAndMortyApi.getCharacters(
                any(),
                any()
            )
        }.returns(Result.failure(TestException()))

        val result =
            updater.updateFromRemote(query = query, page = key, shouldClearLocalCache = true)

        coVerify { rickAndMortyApi.getCharacters(page = key, query = query) }
        coVerify(exactly = 0) {
            localDataSource.upsertAll(
                responseCharacters,
                clear = true,
                query = query
            )
        }
        TestCase.assertEquals(Result.failure<RMCharacters>(TestException()), result)
    }

    @Test
    fun `updateFromRemote() returns Result_failure if saving data fails`() = runTest {
        coEvery { localDataSource.upsertAll(any(), any(), any()) }.throws(TestException())

        val result =
            updater.updateFromRemote(query = query, page = key, shouldClearLocalCache = true)

        coVerify { rickAndMortyApi.getCharacters(page = key, query = query) }
        coVerify { localDataSource.upsertAll(responseCharacters, clear = true, query = query) }
        TestCase.assertEquals(Result.failure<RMCharacters>(TestException()), result)
    }
}
