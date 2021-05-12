package feature.islands.domain.usecase

import com.example.core.model.Mapper
import com.example.core.model.buildRight
import com.example.feature.islands.domain.model.Hemisphere
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.domain.model.IslandRequestInfo
import com.example.feature.islands.domain.repository.IslandRepository
import com.example.feature.islands.domain.usecase.CreateIsland
import com.example.feature.islands.infrastructure.model.IslandCreationRequest
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val USER_ID = "userId"
private const val ISLAND_ID = 1
private const val ISLAND_NAME = "islandName"
private const val NUMBER_OF_VILLAGERS = 12
private const val LAST_VISITED = 0L

@ExperimentalCoroutinesApi
internal class CreateIslandTest {

    private val islandRepository: IslandRepository = mockk()
    private val islandCreationRequestToIslandRequestInfoMapper: Mapper<IslandRequestInfo, IslandCreationRequest> = mockk()
    private lateinit var sut: CreateIsland

    @BeforeEach
    fun setUp() {
        sut = CreateIsland(
            islandRepository,
            islandCreationRequestToIslandRequestInfoMapper
        )

    }

    @Test
    fun `invoke should query islandRepository getIslandById when createIsland returns Right`() = runBlockingTest {
        val islandInfo = IslandInfo(
            ISLAND_NAME,
            Hemisphere.NORTH,
            NUMBER_OF_VILLAGERS,
            LAST_VISITED
        )

        coEvery {
            islandRepository.createIsland(USER_ID, islandInfo)
        } coAnswers { ISLAND_ID.buildRight() }
        coEvery {
            islandRepository.getIslandById(ISLAND_ID)
        } coAnswers { islandInfo.buildRight() }

        val actualResult = sut(USER_ID, islandInfo)

        assertThat(actualResult).isEqualTo(islandInfo.buildRight())
    }

    @Test
    fun `invoke should return Left when createIsland returns a Left`() = runBlockingTest {
        val islandInfo = IslandInfo(
            ISLAND_NAME,
            Hemisphere.NORTH,
            NUMBER_OF_VILLAGERS,
            LAST_VISITED
        )

        coEvery {
            islandRepository.createIsland(USER_ID, islandInfo)
        } coAnswers { InvalidUserIdFailure("").buildLeft() }

        val actualResult = sut(USER_ID, islandInfo)

        assertThat(actualResult).isEqualTo(InvalidUserIdFailure("").buildLeft())
    }
}