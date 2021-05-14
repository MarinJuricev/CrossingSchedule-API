package feature.islands.domain.usecase

import com.example.core.model.Failure
import com.example.core.model.Mapper
import com.example.core.model.buildLeft
import com.example.core.model.buildRight
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

@ExperimentalCoroutinesApi
internal class CreateIslandTest {

    private val islandRepository: IslandRepository = mockk()
    private val islandCreationRequestToIslandRequestInfoMapper: Mapper<IslandRequestInfo, IslandCreationRequest> =
        mockk()
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
        val islandCreationRequest: IslandCreationRequest = mockk()
        val islandRequestInfo: IslandRequestInfo = mockk()
        val islandInfo: IslandInfo = mockk()

        coEvery {
            islandCreationRequestToIslandRequestInfoMapper.map(islandCreationRequest)
        } coAnswers { islandRequestInfo }
        coEvery {
            islandRepository.createIsland(USER_ID, islandRequestInfo)
        } coAnswers { ISLAND_ID.buildRight() }
        coEvery {
            islandRepository.getIslandById(ISLAND_ID)
        } coAnswers { islandInfo.buildRight() }

        val actualResult = sut(USER_ID, islandCreationRequest)

        assertThat(actualResult).isEqualTo(islandInfo.buildRight())
    }

    @Test
    fun `invoke should return Left when createIsland returns a Left`() = runBlockingTest {
        val islandCreationRequest: IslandCreationRequest = mockk()
        val islandRequestInfo: IslandRequestInfo = mockk()

        coEvery {
            islandCreationRequestToIslandRequestInfoMapper.map(islandCreationRequest)
        } coAnswers { islandRequestInfo }
        coEvery {
            islandRepository.createIsland(USER_ID, islandRequestInfo)
        } coAnswers { Failure("").buildLeft() }

        val actualResult = sut(USER_ID, islandCreationRequest)

        assertThat(actualResult).isEqualTo(Failure("").buildLeft())
    }
}