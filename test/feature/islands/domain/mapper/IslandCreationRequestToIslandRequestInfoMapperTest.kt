package feature.islands.domain.mapper

import com.example.core.model.Mapper
import com.example.feature.islands.domain.mapper.IslandCreationRequestToIslandRequestInfoMapper
import com.example.feature.islands.domain.model.Hemisphere
import com.example.feature.islands.domain.model.IslandRequestInfo
import com.example.feature.islands.infrastructure.model.IslandCreationRequest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val ISLAND_NAME = "ISLAND_NAME"
private const val NUMBER_OF_VILLAGERS = 12
private val HEMISPHERE = Hemisphere.NORTH

@ExperimentalCoroutinesApi
internal class IslandCreationRequestToIslandRequestInfoMapperTest {

    private lateinit var sut: Mapper<IslandRequestInfo, IslandCreationRequest>

    @BeforeEach
    fun setUp() {
        sut = IslandCreationRequestToIslandRequestInfoMapper()
    }

    @Test
    fun `map should return a valid IslandRequestInfo instance`() = runBlockingTest {
        val origin = IslandCreationRequest(
            islandName = ISLAND_NAME,
            hemisphere = HEMISPHERE,
            numberOfVillagers = NUMBER_OF_VILLAGERS,
        )

        val actualResult = sut.map(origin)
        val expectedResult = IslandRequestInfo(
            islandName = ISLAND_NAME,
            hemisphere = HEMISPHERE,
            numberOfVillagers = NUMBER_OF_VILLAGERS,
        )

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}