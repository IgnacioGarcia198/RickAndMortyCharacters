package com.ignacio.rickandmorty.framework.local.datasource

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.ignacio.rickandmorty.characters.data.models.RMCharacter
import com.ignacio.rickandmorty.characters.data.paging.datasource.local.CharactersLocalPagingDataSource
import com.ignacio.rickandmorty.characters.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.framework.local.db.AppDatabase
import com.ignacio.rickandmorty.framework.local.db.dao.RMCharacterDao
import com.ignacio.rickandmorty.framework.local.mapping.toDb
import com.ignacio.rickandmorty.framework.local.models.DbRMCharacter
import com.ignacio.rickandmorty.framework.local.sql.RealSqlQueryBuilder
import com.ignacio.rickandmorty.framework.local.sql.SqlQueryBuilder
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
        RealCharactersLocalDataSource(database, RealSqlQueryBuilder())
    private val characters = listOf(RMCharacter.dummy.copy(name = "hello"))
    private val query = CharacterQueryCriteria.default.copy(name = "hello")
    private val pagingSource: PagingSource<Int, DbRMCharacter> = mockk()

    @Before
    fun setUp() {
        val blockSlot = slot<suspend () -> Unit>()
        mockkStatic("androidx.room.RoomDatabaseKt")
        coEvery { database.withTransaction(capture(blockSlot)) }.coAnswers { blockSlot.captured.invoke() }
        coEvery { dao.upsertAll(any()) }.just(runs)
        coEvery { dao.deleteByQuery(any<SqlQueryBuilder.SqlQueryData>()) }.returns(1)
        coEvery { dao.getRMCharacters(any<SqlQueryBuilder.SqlQueryData>()) }.returns(pagingSource)
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
        coVerify {
            dao.deleteByQuery(
                SqlQueryBuilder.SqlQueryData(
                    sqlText = "DELETE FROM rickAndMortyCharacters WHERE name LIKE ?",
                    args = listOf("%${query.name}%")
                )
            )
        }
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
        coVerify {
            dao.deleteByQuery(
                SqlQueryBuilder.SqlQueryData(
                    sqlText = "DELETE FROM rickAndMortyCharacters WHERE name LIKE ? AND type LIKE ? AND species LIKE ? AND status LIKE ? AND gender LIKE ?",
                    args = listOf(
                        "%${criteria.name}%",
                        "%${criteria.type}%",
                        "%${criteria.species}%",
                        "%${criteria.status.name}%",
                        "%${criteria.gender.name}%",
                    )
                )
            )
        }
    }

    @Test
    fun `getRMCharacters() uses dao to get characters PagingDatSource`() = runTest {
        val source = dataSource.getRMCharacters(query)

        verify {
            dao.getRMCharacters(
                SqlQueryBuilder.SqlQueryData(
                    sqlText = "SELECT * FROM rickAndMortyCharacters WHERE name LIKE ?",
                    listOf("%${query.name}%")
                )
            )
        }

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

            verify {
                dao.getRMCharacters(
                    SqlQueryBuilder.SqlQueryData(
                        sqlText = "SELECT * FROM rickAndMortyCharacters WHERE name LIKE ? AND type LIKE ? AND species LIKE ? AND status LIKE ? AND gender LIKE ?",
                        args = listOf(
                            "%${criteria.name}%",
                            "%${criteria.type}%",
                            "%${criteria.species}%",
                            "%${criteria.status.name}%",
                            "%${criteria.gender.name}%",
                        )
                    )
                )
            }

            assertEquals(pagingSource, source)
        }
}
