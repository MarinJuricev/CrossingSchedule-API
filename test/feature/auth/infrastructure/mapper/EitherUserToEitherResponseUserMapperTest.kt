package feature.auth.infrastructure.mapper

import com.example.core.model.Either
import com.example.core.model.Mapper
import com.example.core.model.buildLeft
import com.example.core.model.buildRight
import com.example.fakeUser
import com.example.feature.auth.domain.model.AuthFailure
import com.example.feature.auth.domain.model.AuthFailure.InvalidLoginFailure
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.infrastructure.ResponseUser
import com.example.feature.auth.infrastructure.mapper.EitherUserToEitherResponseUserMapper
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val USER_ID = "userId"
private const val USERNAME = "username"

@ExperimentalCoroutinesApi
internal class EitherUserToEitherResponseUserMapperTest {

    private lateinit var sut: Mapper<Either<AuthFailure, ResponseUser>, Either<AuthFailure, User>>

    @BeforeEach
    fun setUp() {
        sut = EitherUserToEitherResponseUserMapper()
    }

    @Test
    fun `map should return Either Right Response User when the origin is Either Right`() = runBlockingTest {
        val origin = fakeUser.copy(
            id = USER_ID,
            username = USERNAME
        ).buildRight()

        val actualResult = sut.map(origin)
        val expectedResult = ResponseUser(
            id = USER_ID,
            username = USERNAME
        ).buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `map should return provided origin when origin is Either Left`() = runBlockingTest {
        val origin = InvalidLoginFailure().buildLeft()

        val actualResult = sut.map(origin)

        assertThat(actualResult).isEqualTo(origin)
    }
}