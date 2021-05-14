package feature.islands.data.repository

import com.example.core.model.Failure
import com.example.core.model.buildLeft
import com.example.core.model.buildRight
import com.example.feature.islands.data.dao.IslandDao
import com.example.feature.islands.data.repository.IslandRepositoryImpl
import com.example.feature.islands.domain.model.Hemisphere
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.domain.model.IslandRequestInfo
import com.example.feature.islands.domain.repository.IslandRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val USER_ID = "userId"
private const val ISLAND_ID = 0

@ExperimentalCoroutinesApi
class IslandRepositoryImplTest {

    private val islandDao: IslandDao = mockk()
    private lateinit var sut: IslandRepository

    @BeforeEach
    fun setUp() {
        sut = IslandRepositoryImpl(
            islandDao
        )
    }

    @Test
    fun `createIsland should return the islandId returned from the dao`() = runBlockingTest {
        val islandRequestInfo: IslandRequestInfo = mockk()
        coEvery {
            islandDao.createIsland(USER_ID, islandRequestInfo)
        } coAnswers { ISLAND_ID }

        val actualResult = sut.createIsland(USER_ID, islandRequestInfo)

        assertThat(actualResult).isEqualTo(ISLAND_ID.buildRight())
    }

    @Test
    fun `getIslandById should return islandInfo when islandDao returns a valid islandInfo object`() = runBlockingTest {
        val islandInfo: IslandInfo = mockk()
        coEvery {
            islandDao.getIslandById(ISLAND_ID)
        } coAnswers { islandInfo }

        val actualResult = sut.getIslandById(ISLAND_ID)

        assertThat(actualResult).isEqualTo(islandInfo.buildRight())
    }

    @Test
    fun `getIslandById should return IslandNotFoundFailure when islandDao returns null`() = runBlockingTest {
        coEvery {
            islandDao.getIslandById(ISLAND_ID)
        } coAnswers { null }

        val actualResult = sut.getIslandById(ISLAND_ID)
        val expectedResult = Failure("Island for id $ISLAND_ID was not found").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `getIslandForGivenUser should return islandInfo list from islandDao`() = runBlockingTest {
        val islandInfo: IslandInfo = mockk()
        val islandInfoList = listOf(islandInfo)
        coEvery {
            islandDao.getIslandsFromUserId(USER_ID)
        } coAnswers { islandInfoList }

        val actualResult = sut.getIslandsForGivenUser(USER_ID)
        val expectedResult = islandInfoList.buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}