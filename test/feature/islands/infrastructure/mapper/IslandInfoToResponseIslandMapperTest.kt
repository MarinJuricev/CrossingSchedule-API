package feature.islands.infrastructure.mapper

import com.example.core.model.Mapper
import com.example.feature.islands.domain.model.Hemisphere
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.infrastructure.mapper.IslandInfoToResponseIslandMapper
import com.example.feature.islands.infrastructure.model.ResponseIsland
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val ISLAND_ID = 1
private const val ISLAND_NAME = "ISLAND_NAME"
private const val NUMBER_OF_VILLAGERS = 12
private const val LAST_VISITED = 0L
private val HEMISPHERE = Hemisphere.NORTH

@ExperimentalCoroutinesApi
internal class IslandInfoToResponseIslandMapperTest {

    private lateinit var sut: Mapper<ResponseIsland, IslandInfo>

    @BeforeEach
    fun setUp() {
        sut = IslandInfoToResponseIslandMapper()
    }

    @Test
    fun `map should return valid ResponseIsland instance`() = runBlockingTest {
        val islandInfo = IslandInfo(
            islandId = ISLAND_ID,
            islandName = ISLAND_NAME,
            hemisphere = HEMISPHERE,
            numberOfVillagers = NUMBER_OF_VILLAGERS,
            lastVisited = LAST_VISITED,
        )

        val actualResult = sut.map(islandInfo)
        val expectedResult = ResponseIsland(
            islandId = ISLAND_ID,
            islandName = ISLAND_NAME,
            hemisphere = HEMISPHERE,
            numberOfVillagers = NUMBER_OF_VILLAGERS,
            lastVisited = LAST_VISITED
        )

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}