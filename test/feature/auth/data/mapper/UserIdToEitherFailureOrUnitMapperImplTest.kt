package feature.auth.data.mapper

import com.example.core.model.buildLeft
import com.example.core.model.buildRight
import com.example.feature.auth.data.mapper.UserIdToEitherFailureOrUnitMapper
import com.example.feature.auth.data.mapper.UserIdToEitherFailureOrUnitMapperImpl
import com.example.feature.auth.domain.model.AuthFailure.ErrorWhileCreatingUserAccount
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val PROVIDED_USER_ID = "userId"
private const val DIFFERENT_PROVIDED_USER_ID = "differentUserId"

@ExperimentalCoroutinesApi
internal class UserIdToEitherFailureOrUnitMapperImplTest {

    lateinit var sut: UserIdToEitherFailureOrUnitMapper

    @BeforeEach
    fun setUp() {
        sut = UserIdToEitherFailureOrUnitMapperImpl()
    }

    @Test
    fun `map should return Right Unit when the providedUserId and returnedUserId match`() = runBlockingTest {
        val actualResult = sut.map(PROVIDED_USER_ID, PROVIDED_USER_ID)
        val expectedResult = PROVIDED_USER_ID.buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `map should return Left AuthFailure when the providedUserId and returnedUserId dont match`() = runBlockingTest {
        val actualResult = sut.map(PROVIDED_USER_ID, DIFFERENT_PROVIDED_USER_ID)
        val expectedResult = ErrorWhileCreatingUserAccount().buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}