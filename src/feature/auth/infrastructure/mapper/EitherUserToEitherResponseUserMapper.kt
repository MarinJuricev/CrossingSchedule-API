package com.example.feature.auth.infrastructure.mapper

import com.example.core.model.Either
import com.example.core.model.Either.*
import com.example.core.model.Failure
import com.example.core.model.Mapper
import com.example.core.model.buildRight
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.infrastructure.ResponseUser

class EitherUserToEitherResponseUserMapper : Mapper<Either<Failure, ResponseUser>, Either<Failure, User>> {

    override suspend fun map(origin: Either<Failure, User>): Either<Failure, ResponseUser> =
        when (origin) {
            is Left -> origin
            is Right -> toResponseUser(origin.value)
        }

    private fun toResponseUser(
        value: User
    ): Either<Failure, ResponseUser> =
        with(value) {
            ResponseUser(
                id = id,
                username = username
            ).buildRight()
        }
}