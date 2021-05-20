package com.example.feature.auth.data.mapper

import com.example.core.model.Either
import com.example.core.model.Failure
import com.example.core.model.buildLeft
import com.example.core.model.buildRight

interface UserIdToEitherFailureOrUnitMapper {
    suspend fun map(
        providedUserId: String,
        returnedUserId: String,
    ): Either<Failure, String>
}

class UserIdToEitherFailureOrUnitMapperImpl : UserIdToEitherFailureOrUnitMapper {

    override suspend fun map(
        providedUserId: String,
        returnedUserId: String
    ): Either<Failure, String> =
        // If the provided userId is the same as the one in our DB everything worked as expected
        if (providedUserId == returnedUserId) {
            returnedUserId.buildRight()
        } else {
            Failure("Error while creating user account").buildLeft()
        }
}