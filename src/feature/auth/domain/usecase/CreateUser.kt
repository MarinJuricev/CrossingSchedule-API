package com.example.feature.auth.domain.usecase

import com.example.core.model.Either
import com.example.core.model.Failure
import com.example.feature.auth.domain.model.AuthFailure
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.domain.repository.AuthRepository

class CreateUser(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        userId: String,
        username: String,
    ): Either<Failure, User> {
        return when (val result = authRepository.createUser(userId, username)) {
            // We got the userId now fetch it
            // TODO: Research how to return the created object from the create query... surely we don't have to fetch it ourselves
            is Either.Right -> authRepository.getUserById(result.value)
            is Either.Left -> result
        }
    }
}