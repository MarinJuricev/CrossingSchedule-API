package com.example.feature.auth.data.mapper

import com.example.core.model.Either
import com.example.core.model.buildLeft
import com.example.core.model.buildRight
import com.example.feature.auth.domain.model.AuthFailure
import com.example.feature.auth.domain.model.AuthFailure.ErrorWhileCreatingUserAccountFailure

interface UserIdToEitherFailureOrUnitMapper {
    suspend fun map(
        providedUserId: String,
        returnedUserId: String,
    ): Either<AuthFailure, String>
}

class UserIdToEitherFailureOrUnitMapperImpl : UserIdToEitherFailureOrUnitMapper {

    override suspend fun map(
        providedUserId: String,
        returnedUserId: String
    ): Either<AuthFailure, String> =
        // If the provided userId is the same as the one in our DB everything worked as expected
        if (providedUserId == returnedUserId) {
            returnedUserId.buildRight()
        } else {
            ErrorWhileCreatingUserAccountFailure().buildLeft()
        }
}