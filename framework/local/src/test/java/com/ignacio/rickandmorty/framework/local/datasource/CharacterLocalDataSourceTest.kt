package com.ignacio.rickandmorty.framework.local.datasource

import com.ignacio.rickandmorty.characters.data.datasources.local.CharactersLocalDataSource
import com.ignacio.rickandmorty.characters.data.models.RMCharacter
import com.ignacio.rickandmorty.framework.local.db.AppDatabase
import com.ignacio.rickandmorty.framework.local.db.dao.RMCharacterDao
import com.ignacio.rickandmorty.framework.local.mapping.toData
import com.ignacio.rickandmorty.framework.local.models.DbRMCharacter
import com.ignacio.rickandmorty.framework.local.sql.RealSqlQueryBuilder
import com.ignacio.rickandmorty.kotlin_utils.exceptions.TestException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterLocalDataSourceTest {
    private val dao: RMCharacterDao = mockk()
    private val db: AppDatabase = mockk {
        every { rmCharacterDao() }.returns(dao)
    }

    private val dataSource: CharactersLocalDataSource = RealCharactersLocalDataSource(
        db, RealSqlQueryBuilder()
    )
    private val id = 1
    private val daoFlow: MutableStateFlow<DbRMCharacter?> = MutableStateFlow(null)

    @Before
    fun setUp() {
        every { dao.getRMCharacterById(any()) }.returns(daoFlow)
    }

    @Test
    fun `dataSource uses RMCharacterDao to get character by id`() = runTest {
        var result = dataSource.getRMCharacterById(id).first()
        advanceUntilIdle()

        verify { dao.getRMCharacterById(id) }
        TestCase.assertEquals(
            Result.success(null),
            result
        )


        daoFlow.value = DbRMCharacter.dummy
        result = dataSource.getRMCharacterById(id).first()
        advanceUntilIdle()

        TestCase.assertEquals(Result.success(DbRMCharacter.dummy.toData()), result)
    }

    @Test
    fun `getRMCharacterById() returns Flow of Result_failure if db operation fails`() = runTest {
        every { dao.getRMCharacterById(any()) }.returns(flow { throw TestException() })

        val result = dataSource.getRMCharacterById(id).first()
        advanceUntilIdle()

        verify { dao.getRMCharacterById(id) }
        TestCase.assertEquals(
            Result.failure<RMCharacter>(TestException()),
            result
        )
    }
}
