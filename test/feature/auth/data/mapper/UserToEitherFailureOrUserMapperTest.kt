package feature.auth.data.mapper

import com.example.core.model.Either
import com.example.core.model.Mapper
import com.example.core.model.buildLeft
import com.example.core.model.buildRight
import com.example.feature.auth.data.mapper.UserToEitherFailureOrUserMapper
import com.example.feature.auth.domain.model.AuthFailure
import com.example.feature.auth.domain.model.AuthFailure.*
import com.example.feature.auth.domain.model.User
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class UserToEitherFailureOrUserMapperTest {

    lateinit var sut: Mapper<Either<AuthFailure, User>, User?>

    @BeforeEach
    fun setUp() {
        sut = UserToEitherFailureOrUserMapper()
    }

    @Test
    fun `map should return Right User when origin is not null`() = runBlockingTest {
        val origin = User("", "")

        val actualResult = sut.map(origin)
        val expectedResult = origin.buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `map should return Left AuthFailure when origin is null`() = runBlockingTest {
        val actualResult = sut.map(null)
        val expectedResult = NoUserPresentForTokenFailure().buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}