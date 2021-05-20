package com.example.feature.auth.domain.usecase

import com.example.core.model.Either
import com.example.core.model.Failure
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.domain.repository.AuthRepository

class GetUserById(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(userId: String): Either<Failure, User> =
        authRepository.getUserById(userId)
}