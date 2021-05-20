package feature.islands.domain.usecase

import com.example.core.model.buildRight
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.domain.repository.IslandRepository
import com.example.feature.islands.domain.usecase.GetIslands
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val USER_ID = "userId"

@ExperimentalCoroutinesApi
internal class GetIslandsTest {

    private val islandRepository: IslandRepository = mockk()

    private lateinit var sut: GetIslands

    @BeforeEach
    fun setUp() {
        sut = GetIslands(
            islandRepository,
        )
    }

    @Test
    fun `invoke should return result from repository getIslandsForGivenUser`() = runBlockingTest {
        val repositoryResult = mockk<List<IslandInfo>>().buildRight()
        coEvery {
            islandRepository.getIslandsForGivenUser(USER_ID)
        } coAnswers { repositoryResult }

        val actualResult = sut(USER_ID)

        assertThat(actualResult).isEqualTo(repositoryResult)
    }
}