package com.ignacio.rickandmorty.framework.local.db.dao

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import androidx.paging.testing.asPagingSourceFactory
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ignacio.rickandmorty.characters.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.characters.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.characters.data.paging.pager.RealCharactersPagerFactory
import com.ignacio.rickandmorty.framework.local.db.DatabaseTest
import com.ignacio.rickandmorty.framework.local.models.DbRMCharacter
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class RMCharacterDaoTest : DatabaseTest() {
    private lateinit var dao: RMCharacterDao
    private val newCharacters = listOf(
        // empty list fails equality!?
        DbRMCharacter.dummy.copy(episode = listOf("")),
        DbRMCharacter.dummy.copy(name = "hello", episode = listOf("")),
    )
    private val query = CharacterQueryCriteria.default


    @Before
    fun setUp() {
        dao = db.rmCharacterDao()
    }

    @Test
    fun insertCharacterAndGetItById() = runBlocking {
        dao.upsertAll(newCharacters.subList(0, 1))

        val firstCharacter = dao.getRMCharacterById(1).first()

        assertEquals(newCharacters.first(), firstCharacter)
    }

    @Test
    fun insertCharacterAndDeleteByQuery() = runBlocking {
        dao.upsertAll(newCharacters.subList(1, 2))

        var firstCharacter = dao.getRMCharacterById(1).first()

        assertEquals(newCharacters[1], firstCharacter)


        dao.deleteByQuery(SimpleSQLiteQuery("DELETE FROM ${DbRMCharacter.TABLE_NAME} WHERE name LIKE '%l%'"))
        firstCharacter = dao.getRMCharacterById(1).first()

        assertEquals(null, firstCharacter)
    }

    @Test
    fun insertCharactersAndClear() = runBlocking {
        dao.upsertAll(newCharacters)

        var firstCharacter = dao.getRMCharacterById(1).first()

        assertNotNull(firstCharacter)


        dao.clearAll()
        firstCharacter = dao.getRMCharacterById(1).first()

        assertNull(firstCharacter)
    }

    @Test
    fun insertCharactersAndGetPagingSource() = runBlocking {
        dao.upsertAll(newCharacters.subList(0, 1))

        val pager = TestPager(
            config = PagingConfig(pageSize = RealCharactersPagerFactory.DB_PAGE_SIZE),
            pagingSource = dao.getRMCharacters(SimpleSQLiteQuery("SELECT * FROM ${DbRMCharacter.TABLE_NAME}"))
        )

        val result = pager.refresh() as PagingSource.LoadResult.Page

        assertEquals(newCharacters.subList(0, 1), result.data)
    }
}
