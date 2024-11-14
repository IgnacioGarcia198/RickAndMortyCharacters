package com.ignacio.rickandmorty.framework.local.datasource

import androidx.paging.PagingSource
import androidx.room.withTransaction
import androidx.sqlite.db.SupportSQLiteQuery
import com.ignacio.rickandmorty.data.models.RMCharacter
import com.ignacio.rickandmorty.data.paging.datasource.local.CharactersLocalPagingDataSource
import com.ignacio.rickandmorty.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.framework.local.db.AppDatabase
import com.ignacio.rickandmorty.framework.local.db.dao.RMCharacterDao
import com.ignacio.rickandmorty.framework.local.mapping.toDb
import com.ignacio.rickandmorty.framework.local.models.DbRMCharacter
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.slot
import io.mockk.unmockkStatic
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class CharacterLocalPagingDataSourceTest {

    private val dao: RMCharacterDao = mockk()
    private val database: AppDatabase = mockk {
        every { rmCharacterDao() }.returns(dao)
    }
    private val dataSource: CharactersLocalPagingDataSource =
        RealCharactersLocalDataSource(database)
    private val characters = listOf(RMCharacter.dummy.copy(name = "hello"))
    private val query = CharacterQueryCriteria.default.copy(name = "hello")
    private val dbCharacters = listOf(DbRMCharacter.dummy)


    private val pagingSource: PagingSource<Int, DbRMCharacter> = mockk()

    @Before
    fun setUp() {
        val blockSlot = slot<suspend () -> Unit>()
        mockkStatic("androidx.room.RoomDatabaseKt")
        coEvery { database.withTransaction(capture(blockSlot)) }.coAnswers { blockSlot.captured.invoke() }
        coEvery { dao.upsertAll(any()) }.just(runs)
        coEvery { dao.deleteByQuery(any()) }.returns(1)
        coEvery { dao.getRMCharacters(any()) }.returns(pagingSource)
    }

    @After
    fun tearDown() {
        unmockkStatic("androidx.room.RoomDatabaseKt")
    }

    @Test
    fun `upsertAll uses dao to upsert data`() = runTest {
        dataSource.upsertAll(characters, false, query)

        coVerify { database.withTransaction(any()) }
        coVerify { dao.upsertAll(characters = characters.map { it.toDb() }) }
    }

    @Test
    fun `upsertAll clears data if clear is true`() = runTest {
        dataSource.upsertAll(characters, true, query)

        coVerify { database.withTransaction(any()) }
        coVerify { dao.upsertAll(characters = characters.map { it.toDb() }) }
        val slot = slot<SupportSQLiteQuery>()
        coVerify { dao.deleteByQuery(capture(slot)) }
        val captured = slot.captured
        val sql = captured.sql
        assertEquals(sql, "DELETE FROM rickAndMortyCharacters WHERE name LIKE ?")
        assertEquals(1, captured.argCount)
    }

    @Test
    fun `upsertAll uses all the non-empty query fields to clear data`() = runTest {
        val criteria = CharacterQueryCriteria(
            name = "name",
            status = CharacterQueryCriteria.Status.ALIVE,
            species = "species",
            type = "type",
            gender = CharacterQueryCriteria.Gender.FEMALE
        )
        dataSource.upsertAll(characters, true, criteria)

        coVerify { database.withTransaction(any()) }
        coVerify { dao.upsertAll(characters = characters.map { it.toDb() }) }
        val slot = slot<SupportSQLiteQuery>()
        coVerify { dao.deleteByQuery(capture(slot)) }
        val captured = slot.captured
        val sql = captured.sql
        assertEquals(
            sql,
            "DELETE FROM rickAndMortyCharacters WHERE name LIKE ? AND type LIKE ? AND species LIKE ? AND status LIKE ? AND gender LIKE ?"
        )
        assertEquals(5, captured.argCount)
    }

    @Test
    fun `getRMCharacters() uses dao to get characters PagingDatSource`() = runTest {
        val source = dataSource.getRMCharacters(query)

        val slot = slot<SupportSQLiteQuery>()
        verify { dao.getRMCharacters(capture(slot)) }

        val captured = slot.captured
        val sql = captured.sql
        assertEquals("SELECT * FROM rickAndMortyCharacters WHERE name LIKE ?", sql)
        assertEquals(1, captured.argCount)

        assertEquals(pagingSource, source)
    }

    @Test
    fun `getRMCharacters() uses all the non-empty query fields to get characters PagingDatSource`() =
        runTest {
            val criteria = CharacterQueryCriteria(
                name = "name",
                status = CharacterQueryCriteria.Status.ALIVE,
                species = "species",
                type = "type",
                gender = CharacterQueryCriteria.Gender.FEMALE
            )

            val source = dataSource.getRMCharacters(criteria)

            val slot = slot<SupportSQLiteQuery>()
            verify { dao.getRMCharacters(capture(slot)) }

            val captured = slot.captured
            val sql = captured.sql
            assertEquals(
                sql,
                "SELECT * FROM rickAndMortyCharacters WHERE name LIKE ? AND type LIKE ? AND species LIKE ? AND status LIKE ? AND gender LIKE ?"
            )
            assertEquals(5, captured.argCount)

            assertEquals(pagingSource, source)
        }
}