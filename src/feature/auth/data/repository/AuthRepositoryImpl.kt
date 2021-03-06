package com.example.feature.auth.data.repository

import com.example.core.model.Either
import com.example.core.model.Failure
import com.example.core.model.Mapper
import com.example.feature.auth.data.dao.AuthDao
import com.example.feature.auth.data.mapper.UserIdToEitherFailureOrUnitMapper
import com.example.feature.auth.domain.model.AuthFailure
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authDao: AuthDao,
    private val userToEitherFailureOrUserMapper: Mapper<Either<Failure, User>, User?>,
    private val userIdToEitherFailureOrUnitMapper: UserIdToEitherFailureOrUnitMapper,
) : AuthRepository {
    override suspend fun getUserById(
        userId: String
    ): Either<Failure, User> = userToEitherFailureOrUserMapper.map(
        authDao.getUserById(userId)
    )

    override suspend fun createUser(
        userId: String,
        username: String,
    ): Either<Failure, String> = userIdToEitherFailureOrUnitMapper.map(
        authDao.createUser(userId, username),
        userId
    )
}