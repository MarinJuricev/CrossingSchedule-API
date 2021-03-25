package com.example.feature.auth.di

import com.example.core.model.Either
import com.example.core.model.Mapper
import com.example.feature.auth.data.dao.AuthDao
import com.example.feature.auth.data.dao.Users
import com.example.feature.auth.data.mapper.UserIdToEitherFailureOrUnitMapper
import com.example.feature.auth.data.mapper.UserIdToEitherFailureOrUnitMapperImpl
import com.example.feature.auth.data.mapper.UserToEitherFailureOrUserMapper
import com.example.feature.auth.data.repository.AuthRepositoryImpl
import com.example.feature.auth.domain.model.AuthFailure
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.domain.repository.AuthRepository
import com.example.feature.auth.domain.usecase.CreateUser
import com.example.feature.auth.domain.usecase.GetUserById
import com.example.feature.auth.domain.usecase.GetUserFromToken
import com.example.feature.auth.infrastructure.AuthService
import com.example.feature.auth.infrastructure.AuthServiceImpl
import com.example.feature.auth.infrastructure.ResponseUser
import com.example.feature.auth.infrastructure.mapper.CreateUserServiceMapper
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val userToEitherFailureOrUserMapperName = UserToEitherFailureOrUserMapper::class.java.name
private val createUserServiceMapperName = CreateUserServiceMapper::class.java.name

val authModule = module {
    single<AuthDao> { Users }
    single<FirebaseAuth> { FirebaseAuth.getInstance() }

    factory<Mapper<Either<AuthFailure, User>, User?>>(
        named(userToEitherFailureOrUserMapperName)
    ) {
        UserToEitherFailureOrUserMapper()
    }
    factory<Mapper<Either<AuthFailure, ResponseUser>, Either<AuthFailure, User>>>(
        named(createUserServiceMapperName)
    ) {
        CreateUserServiceMapper()
    }

    factory<UserIdToEitherFailureOrUnitMapper> { UserIdToEitherFailureOrUnitMapperImpl() }

    factory { CreateUser(get()) }
    factory { GetUserById(get()) }
    factory { GetUserFromToken(get()) }
    factory<AuthService> {
        AuthServiceImpl(
            get(),
            get(),
            get(),
            get(named(createUserServiceMapperName))
        )
    }
    factory<AuthRepository> {
        AuthRepositoryImpl(
            get(),
            get(named(userToEitherFailureOrUserMapperName)),
            get()
        )
    }
}