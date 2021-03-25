package com.example.feature.auth.domain.repository

import com.example.core.model.Either
import com.example.feature.auth.domain.model.AuthFailure
import com.example.feature.auth.domain.model.User

interface AuthRepository {
    suspend fun getUserById(userId: String): Either<AuthFailure, User>
    suspend fun createUser(userId: String, username: String): Either<AuthFailure, String>
}