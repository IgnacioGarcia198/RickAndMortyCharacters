import com.ignacio.rickandmorty.characters.data.paging.mapping.toData
import com.ignacio.rickandmorty.characters.data.paging.mapping.toDomain
import com.ignacio.rickandmorty.characters.data.paging.models.CharacterQueryCriteria
import com.ignacio.rickandmorty.characters.data.paging.models.LocalRMCharacter
import com.ignacio.rickandmorty.characters.data.paging.models.TestLocalRMCharacter
import com.ignacio.rickandmorty.characters.data.paging.repository.RealRMCharactersPagingRepository
import com.ignacio.rickandmorty.characters.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.characters.domain.repository.RMCharactersPagingRepository
import com.ignacio.rickandmorty.kotlin_utils.paging.TestPagedData
import com.ignacio.rickandmorty.kotlin_utils.paging.TestPager
import com.ignacio.rickandmorty.paging.pager.PagerFactory
import io.mockk.every
import io.mockk.mockk
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
    private val pagerFactory: PagerFactory<Int, LocalRMCharacter, CharacterQueryCriteria> = mockk()
    private val repository: RMCharactersPagingRepository = RealRMCharactersPagingRepository(
        pagerFactory = pagerFactory,
    )
    private val query = CharacterListQueryCriteria.default
    private val dataQuery = query.toData()
    private val testPagerFlow: Flow<TestPagedData<LocalRMCharacter>> = MutableStateFlow(
        TestPagedData(TestLocalRMCharacter.dummy)
    )
    private val testPager = TestPager<Int, LocalRMCharacter>(testPagerFlow)

    @Before
    fun setUp() {
        every { pagerFactory.create(any()) }.coAnswers {
            testPager
        }
    }

    @Test
    fun `getRMCharacters() uses PagerFactory to create a new Pager`() = runTest {
        val data = repository.getRMCharacters(query).first()
        advanceUntilIdle()

        verify { pagerFactory.create(queryCriteria = dataQuery) }
        assertEquals(TestPagedData(TestLocalRMCharacter.dummy.toDomain()), data)
    }
}
