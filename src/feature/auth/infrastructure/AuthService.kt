package com.example.feature.auth.infrastructure

import com.example.core.model.Either
import com.example.core.model.Mapper
import com.example.core.model.buildLeft
import com.example.feature.auth.domain.model.AuthFailure
import com.example.feature.auth.domain.model.AuthFailure.*
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.domain.usecase.CreateUser
import com.example.feature.auth.domain.usecase.GetUserById
import com.example.feature.auth.domain.usecase.GetUserFromToken

interface AuthService {
    suspend fun getUserFromAuthUid(uid: String): User?
    suspend fun createUser(id: String?, username: String?): Either<AuthFailure, ResponseUser>
    suspend fun loginUser(id: String?): Either<AuthFailure, ResponseUser>
}

class AuthServiceImpl(
    private val getUserFromToken: GetUserFromToken,
    private val getUserById: GetUserById,
    private val createUserUseCase: CreateUser,
    private val eitherUserToEitherResponseUserMapper: Mapper<Either<AuthFailure, ResponseUser>, Either<AuthFailure, User>>
) : AuthService {
    override suspend fun getUserFromAuthUid(
        uid: String,
    ): User? = when (val result = getUserFromToken(uid)) {
        is Either.Right -> result.value
        is Either.Left -> null
    }

    override suspend fun createUser(
        id: String?,
        username: String?,
    ): Either<AuthFailure, ResponseUser> {
        return when {
            id == null -> NoUserPresentForTokenFailure().buildLeft()
            username == null -> MissingRequiredArgument("Username is required got: $username").buildLeft()
            else -> when (getUserById(id)) {
                // We got a user back, not desired behavior return a failure
                is Either.Right -> ErrorWhileCreatingUserAccountFailure().buildLeft()
                // No user found for that ID we create one
                is Either.Left -> eitherUserToEitherResponseUserMapper.map(createUserUseCase(id, username))
            }
        }
    }

    override suspend fun loginUser(
        id: String?
    ): Either<AuthFailure, ResponseUser> {
        if (id == null) {
            return InvalidLoginFailure().buildLeft()
        }

        return when (val result = getUserById(id)) {
            is Either.Right -> eitherUserToEitherResponseUserMapper.map(result)
            is Either.Left -> InvalidLoginFailure().buildLeft()
        }
    }
}

