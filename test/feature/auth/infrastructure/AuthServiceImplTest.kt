package feature.auth.infrastructure

import com.example.core.model.Either
import com.example.core.model.Mapper
import com.example.core.model.buildLeft
import com.example.core.model.buildRight
import com.example.fakeResponseUser
import com.example.fakeUser
import com.example.feature.auth.domain.model.AuthFailure
import com.example.feature.auth.domain.model.AuthFailure.*
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.domain.usecase.CreateUser
import com.example.feature.auth.domain.usecase.GetUserById
import com.example.feature.auth.domain.usecase.GetUserFromToken
import com.example.feature.auth.infrastructure.AuthService
import com.example.feature.auth.infrastructure.AuthServiceImpl
import com.example.feature.auth.infrastructure.ResponseUser
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val UUID = "uuid"
private const val ID = "id"
private const val USERNAME = "username"

@ExperimentalCoroutinesApi
internal class AuthServiceImplTest {

    private val getUserFromToken: GetUserFromToken = mockk()
    private val getUserById: GetUserById = mockk()
    private val createUserUseCase: CreateUser = mockk()
    private val eitherUserToEitherResponseUserMapper: Mapper<Either<AuthFailure, ResponseUser>, Either<AuthFailure, User>> =
        mockk()

    private lateinit var sut: AuthService

    @BeforeEach
    fun setUp() {
        sut = AuthServiceImpl(
            getUserFromToken,
            getUserById,
            createUserUseCase,
            eitherUserToEitherResponseUserMapper
        )
    }

    @Test
    fun `getUserFromAuthUid should return a valid User object when getUserFromToken returns Right`() = runBlockingTest {
        val useCaseResult = fakeUser

        coEvery { getUserFromToken(UUID) } coAnswers { useCaseResult.buildRight() }

        val actualResult = sut.getUserFromAuthUid(UUID)

        assertThat(actualResult).isEqualTo(useCaseResult)
    }

    @Test
    fun `getUserFromAuthUid should return null when getUserFromToken returns Left`() = runBlockingTest {
        coEvery { getUserFromToken(UUID) } coAnswers { InvalidLoginFailure().buildLeft() }

        val actualResult = sut.getUserFromAuthUid(UUID)

        assertThat(actualResult).isNull()
    }

    @Test
    fun `createUser should return ErrorWhileCreatingUserAccountFailure when getUserById returns Right`() =
        runBlockingTest {
            val getUserByIdResult = fakeUser.buildRight()

            coEvery { getUserById(ID) } coAnswers { getUserByIdResult }

            val actualResult = sut.createUser(ID, USERNAME)
            assertThat(actualResult).isEqualTo(ErrorWhileCreatingUserAccountFailure().buildLeft())
        }

    @Test
    fun `createUser should return Response user when getUserById returns Left`() =
        runBlockingTest {
            val getUserByIdResult = ErrorWhileCreatingUserAccountFailure().buildLeft()
            val createUserUseCaseResult = fakeUser.buildRight()
            val mapperResult = fakeResponseUser.buildRight()

            coEvery { getUserById(ID) } coAnswers { getUserByIdResult }
            coEvery { createUserUseCase(ID, USERNAME) } coAnswers { createUserUseCaseResult }
            coEvery {
                eitherUserToEitherResponseUserMapper.map(createUserUseCaseResult)
            } coAnswers {
                mapperResult
            }

            val actualResult = sut.createUser(ID, USERNAME)
            assertThat(actualResult).isEqualTo(mapperResult)
        }

    @Test
    fun `createUser should return NoUserPresentForTokenFailure when provided is id null`() = runBlockingTest {
        val actualResult = sut.createUser(null, USERNAME)
        val expectedResult = NoUserPresentForTokenFailure().buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `createUser should return MissingRequiredArgument when provided username is null`() = runBlockingTest {
        val actualResult = sut.createUser(ID, null)
        val expectedResult = MissingRequiredArgument("Username is required got: null").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `loginUser should return InvalidLoginFailure when provided id is null`() = runBlockingTest {
        val actualResult = sut.loginUser(null)
        val expectedResult = InvalidLoginFailure().buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `loginUser should return Response User when getUserById returns Right`() = runBlockingTest {
        val getUserByIdResult = fakeUser.buildRight()
        val mapperResult = fakeResponseUser.buildRight()

        coEvery { getUserById(ID) } coAnswers { getUserByIdResult }
        coEvery {
            eitherUserToEitherResponseUserMapper.map(getUserByIdResult)
        } coAnswers {
            mapperResult
        }

        val actualResult = sut.loginUser(ID)

        assertThat(actualResult).isEqualTo(mapperResult)
    }

    @Test
    fun `loginUser should return InvalidLoginFailure when getUserById returns Left`() = runBlockingTest {
        val getUserByIdResult = ErrorWhileCreatingUserAccountFailure().buildLeft()

        coEvery { getUserById(ID) } coAnswers { getUserByIdResult }

        val actualResult = sut.loginUser(ID)

        assertThat(actualResult).isEqualTo(InvalidLoginFailure().buildLeft())
    }
}