package feature.islands.infrastructure

import com.example.core.model.Failure
import com.example.core.model.Mapper
import com.example.core.model.buildLeft
import com.example.core.model.buildRight
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.domain.usecase.CreateIsland
import com.example.feature.islands.domain.usecase.GetIslands
import com.example.feature.islands.infrastructure.IslandService
import com.example.feature.islands.infrastructure.IslandServiceImpl
import com.example.feature.islands.infrastructure.model.IslandCreationRequest
import com.example.feature.islands.infrastructure.model.ResponseIsland
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val USER_ID = "userId"

@ExperimentalCoroutinesApi
internal class IslandServiceImplTest {

    private val createIslandUseCase: CreateIsland = mockk()
    private val getIslands: GetIslands = mockk()
    private val islandInfoToResponseIslandMapper: Mapper<ResponseIsland, IslandInfo> = mockk()

    private lateinit var sut: IslandService

    @BeforeEach
    fun setUp() {
        sut = IslandServiceImpl(
            createIslandUseCase,
            getIslands,
            islandInfoToResponseIslandMapper,
        )
    }

    @Test
    fun `createIsland should return Failure when the provided userId is null`() = runBlockingTest {
        val request: IslandCreationRequest = mockk()

        val actualResult = sut.createIsland(null, request)
        val expectedResult = Failure("Invalid userId cannot be null got : null").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `createIsland should return Failure when the provided request is null`() = runBlockingTest {
        val actualResult = sut.createIsland(USER_ID, null)
        val expectedResult = Failure("Invalid request cannot be null got : null").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `createIsland should return Failure when the createIslandUseCase returns Left`() = runBlockingTest {
        val request: IslandCreationRequest = mockk()
        coEvery {
            createIslandUseCase(USER_ID, request)
        } coAnswers { Failure.EMPTY.buildLeft() }

        val actualResult = sut.createIsland(USER_ID, request)

        assertThat(actualResult).isEqualTo(Failure.EMPTY.buildLeft())
    }

    @Test
    fun `createIsland should return ResponseIsland when the createIslandUseCase returns Right`() = runBlockingTest {
        val request: IslandCreationRequest = mockk()
        val islandInfo: IslandInfo = mockk()
        val responseIsland: ResponseIsland = mockk()
        coEvery {
            createIslandUseCase(USER_ID, request)
        } coAnswers { islandInfo.buildRight() }
        coEvery {
            islandInfoToResponseIslandMapper.map(islandInfo)
        } coAnswers { responseIsland }

        val actualResult = sut.createIsland(USER_ID, request)

        assertThat(actualResult).isEqualTo(responseIsland.buildRight())
    }

    @Test
    fun `getIslandForGivenUserId should return Failure when the provided userId is null`() = runBlockingTest {
        val actualResult = sut.getIslandForGivenUserId(null)
        val expectedResult = Failure("Invalid userId cannot be null got : null").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `getIslandForGivenUserId should return Failure when the getIslands returns Left`() = runBlockingTest {
        coEvery {
            getIslands(USER_ID)
        } coAnswers { Failure.EMPTY.buildLeft() }

        val actualResult = sut.getIslandForGivenUserId(USER_ID)

        assertThat(actualResult).isEqualTo(Failure.EMPTY.buildLeft())
    }

    @Test
    fun `getIslandForGivenUserId should return List ResponseIsland when the createIslandUseCase returns Right`() =
        runBlockingTest {
            val islandInfo: IslandInfo = mockk()
            val responseIsland: ResponseIsland = mockk()
            val islandInfoList = listOf(islandInfo)
            val responseIslandList = listOf(responseIsland)
            coEvery {
                getIslands(USER_ID)
            } coAnswers { islandInfoList.buildRight() }
            coEvery {
                islandInfoToResponseIslandMapper.map(islandInfo)
            } coAnswers { responseIsland }

            val actualResult = sut.getIslandForGivenUserId(USER_ID)

            assertThat(actualResult).isEqualTo(responseIslandList.buildRight())
        }
}