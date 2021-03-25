package feature.auth.domain.usecase

import com.example.core.model.buildRight
import com.example.fakeUser
import com.example.feature.auth.domain.repository.AuthRepository
import com.example.feature.auth.domain.usecase.GetUserById
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val USER_ID = "userId"

@ExperimentalCoroutinesApi
internal class GetUserByIdTest {

    private val authRepository: AuthRepository = mockk()

    private lateinit var sut: GetUserById

    @BeforeEach
    fun setUp() {
        sut = GetUserById(
            authRepository
        )
    }

    @Test
    fun `invoke should trigger authRepository with the provided userId`() = runBlockingTest {
        val authResult = fakeUser.buildRight()

        coEvery {
            authRepository.getUserById(USER_ID)
        } coAnswers {
            authResult
        }

        val actualResult = sut(USER_ID)

        assertThat(actualResult).isEqualTo(authResult)
    }
}