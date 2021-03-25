package com.example.feature.auth.domain.usecase

import com.example.core.model.Either
import com.example.feature.auth.domain.model.AuthFailure
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.domain.repository.AuthRepository

class GetUserById(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(userId: String): Either<AuthFailure, User> =
        authRepository.getUserById(userId)
}