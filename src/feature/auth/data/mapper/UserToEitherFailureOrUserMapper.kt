package com.example.feature.auth.data.mapper

import com.example.core.model.Either
import com.example.core.model.Mapper
import com.example.core.model.buildLeft
import com.example.core.model.buildRight
import com.example.feature.auth.domain.model.AuthFailure
import com.example.feature.auth.domain.model.AuthFailure.NoUserPresentForTokenFailure
import com.example.feature.auth.domain.model.User

class UserToEitherFailureOrUserMapper : Mapper<Either<AuthFailure, User>, User?> {

    override suspend fun map(origin: User?): Either<AuthFailure, User> =
        origin?.buildRight() ?: NoUserPresentForTokenFailure().buildLeft()
}