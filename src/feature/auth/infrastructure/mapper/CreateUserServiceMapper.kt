package com.example.feature.auth.infrastructure.mapper

import com.example.core.model.Either
import com.example.core.model.Mapper
import com.example.core.model.buildRight
import com.example.feature.auth.domain.model.AuthFailure
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.infrastructure.ResponseUser

class CreateUserServiceMapper : Mapper<Either<AuthFailure, ResponseUser>, Either<AuthFailure, User>> {

    override suspend fun map(origin: Either<AuthFailure, User>): Either<AuthFailure, ResponseUser> =
        when (origin) {
            is Either.Right -> toResponseUser(origin.value)
            is Either.Left -> origin
        }

    private fun toResponseUser(
        value: User
    ): Either<AuthFailure, ResponseUser> =
        with(value) {
            ResponseUser(
                id = id,
                username = username
            ).buildRight()
        }
}