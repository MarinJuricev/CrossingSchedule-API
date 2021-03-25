package feature.auth.domain.usecase

import com.example.core.model.buildLeft
import com.example.core.model.buildRight
import com.example.feature.auth.domain.model.AuthFailure.NoUserPresentForTokenFailure
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.domain.usecase.GetUserFromToken
import com.google.common.truth.Truth.assertThat
import com.google.firebase.ErrorCode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserRecord
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val UID = "UID"
private const val DISPLAY_NAME = "DISPLAY_NAME"
private const val ERROR_MESSAGE = "ERROR_MESSAGE"

internal class GetUserFromTokenTest {

    private val firebaseAuth: FirebaseAuth = mockk()
    private val userRecord: UserRecord = mockk()

    lateinit var sut: GetUserFromToken

    @BeforeEach
    fun setUp() {
        sut = GetUserFromToken(
            firebaseAuth
        )
    }

    @Test
    fun `invoke should return Right User when getUser returns a UserRecord`() {
        every { firebaseAuth.getUser(UID) } answers { userRecord }
        every { userRecord.displayName } answers { DISPLAY_NAME }

        val actualResult = sut(UID)
        val expectedResult = User(
            id = UID,
            username = DISPLAY_NAME
        ).buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke should return a Right User with displayName Anonymous when getUser returns a UserRecord with displayName null`() {
        every { firebaseAuth.getUser(UID) } answers { userRecord }
        every { userRecord.displayName } answers { null }

        val actualResult = sut(UID)
        val expectedResult = User(
            id = UID,
            username = "Anonymous"
        ).buildRight()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke should return Left NoUserPresentForTokenFailure when getUser id throws FirebaseAuthException`() {
        val exception = FirebaseAuthException(
            ErrorCode.ABORTED,
            ERROR_MESSAGE,
            null,
            null,
            null
        )

        every { firebaseAuth.getUser(UID) } throws exception

        val actualResult = sut(UID)
        val expectedResult = NoUserPresentForTokenFailure().buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}