package feature.auth.data.repository

import com.example.core.model.Either
import com.example.core.model.Mapper
import com.example.core.model.buildRight
import com.example.fakeUser
import com.example.feature.auth.data.dao.AuthDao
import com.example.feature.auth.data.mapper.UserIdToEitherFailureOrUnitMapper
import com.example.feature.auth.data.repository.AuthRepositoryImpl
import com.example.feature.auth.domain.model.AuthFailure
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.domain.repository.AuthRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val USER_ID = "userId"
private const val USERNAME = "username"

@ExperimentalCoroutinesApi
internal class AuthRepositoryImplTest {

    private val authDao: AuthDao = mockk()
    private val userToEitherFailureOrUserMapper: Mapper<Either<AuthFailure, User>, User?> = mockk()
    private val userIdToEitherFailureOrUnitMapper: UserIdToEitherFailureOrUnitMapper = mockk()

    private lateinit var sut: AuthRepository

    @BeforeEach
    fun setUp() {
        sut = AuthRepositoryImpl(
            authDao,
            userToEitherFailureOrUserMapper,
            userIdToEitherFailureOrUnitMapper
        )
    }

    @Test
    fun `getUserById should return a result from the dao and then mapped to a Either`() = runBlockingTest {
        val daoResult = fakeUser
        val mapperResult = daoResult.buildRight()

        coEvery {
            authDao.getUserById(USER_ID)
        } coAnswers {
            daoResult
        }
        coEvery {
            userToEitherFailureOrUserMapper.map(daoResult)
        } coAnswers {
            mapperResult
        }

        val actualResult = sut.getUserById(USER_ID)

        assertThat(actualResult).isEqualTo(mapperResult)
    }

    @Test
    fun `createUser should return a result from the dao and then mapped to a Either`() = runBlockingTest {
        val mapperResult = USER_ID.buildRight()

        coEvery {
            authDao.createUser(USER_ID, USERNAME)
        } coAnswers {
            USER_ID
        }
        coEvery {
            userIdToEitherFailureOrUnitMapper.map(USER_ID, USER_ID)
        } coAnswers {
            mapperResult
        }

        val actualResult = sut.createUser(USER_ID, USERNAME)

        assertThat(actualResult).isEqualTo(mapperResult)
    }
}