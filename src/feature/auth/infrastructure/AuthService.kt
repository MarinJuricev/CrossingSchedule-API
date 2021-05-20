package com.example.feature.auth.infrastructure

import com.example.core.model.Either
import com.example.core.model.Either.Left
import com.example.core.model.Either.Right
import com.example.core.model.Failure
import com.example.core.model.Mapper
import com.example.core.model.buildLeft
import com.example.feature.auth.domain.model.AuthFailure
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.domain.usecase.CreateUser
import com.example.feature.auth.domain.usecase.GetUserById
import com.example.feature.auth.domain.usecase.GetUserFromToken

interface AuthService {
    suspend fun getUserFromAuthUid(uid: String): User?
    suspend fun createUser(id: String?, username: String?): Either<Failure, ResponseUser>
    suspend fun loginUser(id: String?): Either<Failure, ResponseUser>
}

class AuthServiceImpl(
    private val getUserFromToken: GetUserFromToken,
    private val getUserById: GetUserById,
    private val createUserUseCase: CreateUser,
    private val eitherUserToEitherResponseUserMapper: Mapper<Either<Failure, ResponseUser>, Either<Failure, User>>
) : AuthService {
    override suspend fun getUserFromAuthUid(
        uid: String,
    ): User? = when (val result = getUserFromToken(uid)) {
        is Right -> result.value
        is Left -> null
    }

    override suspend fun createUser(
        id: String?,
        username: String?,
    ): Either<Failure, ResponseUser> {
        return when {
            id == null -> Failure(AuthFailure.NO_USER_PRESENT_FOR_TOKEN).buildLeft()
            username == null -> Failure("Username is required got: $username").buildLeft()
            else -> when (getUserById(id)) {
                // We got a user back, not desired behavior return a failure
                is Right -> Failure(AuthFailure.ERROR_WHILE_CREATING_USER_ACCOUNT).buildLeft()
                // No user found for that ID we create one
                is Left -> eitherUserToEitherResponseUserMapper.map(createUserUseCase(id, username))
            }
        }
    }

    override suspend fun loginUser(
        id: String?
    ): Either<Failure, ResponseUser> {
        if (id == null) {
            return Failure("No user found, double check credentials").buildLeft()
        }

        return when (val result = getUserById(id)) {
            is Right -> eitherUserToEitherResponseUserMapper.map(result)
            is Left -> Failure("No user found, double check credentials").buildLeft()
        }
    }
}

