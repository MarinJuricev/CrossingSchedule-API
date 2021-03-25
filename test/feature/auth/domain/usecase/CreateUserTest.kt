package feature.auth.domain.usecase

import com.example.core.model.buildLeft
import com.example.core.model.buildRight
import com.example.fakeUser
import com.example.feature.auth.domain.model.AuthFailure.ErrorWhileCreatingUserAccount
import com.example.feature.auth.domain.repository.AuthRepository
import com.example.feature.auth.domain.usecase.CreateUser
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val USER_ID = "userId"
private const val USERNAME = "username"

@ExperimentalCoroutinesApi
internal class CreateUserTest {

    private val authRepository: AuthRepository = mockk()

    lateinit var sut: CreateUser

    @BeforeEach
    fun setUp() {
        sut = CreateUser(
            authRepository
        )
    }

    @Test
    fun `invoke should return the create user result when it returns Left`() = runBlockingTest {
        val createUserResult = ErrorWhileCreatingUserAccount().buildLeft()

        coEvery {
            authRepository.createUser(USER_ID, USERNAME)
        } coAnswers {
            createUserResult
        }

        val actualResult = sut(USER_ID, USERNAME)

        assertThat(actualResult).isEqualTo(createUserResult)

        coVerify(exactly = 0) { authRepository.getUserById(USER_ID) }
    }

    @Test
    fun `invoke should return the getUserById result when createUser returns Right`() = runBlockingTest {
        val createUserResult = USER_ID.buildRight()
        val getUserByIdResult = fakeUser.buildRight()

        coEvery {
            authRepository.createUser(USER_ID, USERNAME)
        } coAnswers {
            createUserResult
        }
        coEvery {
            authRepository.getUserById(USER_ID)
        } coAnswers {
            getUserByIdResult
        }

        val actualResult = sut(USER_ID, USERNAME)

        assertThat(actualResult).isEqualTo(getUserByIdResult)
    }
}