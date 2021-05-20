package com.example.feature.auth.data.mapper

import com.example.core.model.Either
import com.example.core.model.Failure
import com.example.core.model.Mapper
import com.example.core.model.buildLeft
import com.example.core.model.buildRight
import com.example.feature.auth.domain.model.User

class UserToEitherFailureOrUserMapper : Mapper<Either<Failure, User>, User?> {

    override suspend fun map(origin: User?): Either<Failure, User> =
        origin?.buildRight() ?: Failure("No user present for that id").buildLeft()
}