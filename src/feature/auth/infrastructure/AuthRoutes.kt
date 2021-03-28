package com.example.feature.auth.infrastructure

import com.example.core.ext.buildCrossingFailResponse
import com.example.core.ext.buildCrossingSuccessResponse
import com.example.core.model.Either
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.infrastructure.model.SignUpRequest
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.registerAuthRoutes() {
    routing {
        authRoute()
    }
}

fun Route.authRoute() {

    val authService by inject<AuthService>()

    authenticate {
        get("/login") {
            val userFromToken = call.principal<User>()

            when (val result = authService.loginUser(userFromToken?.id)) {
                is Either.Right -> call.buildCrossingSuccessResponse(result.value)
                is Either.Left -> call.buildCrossingFailResponse(result.error.errorMessage)
            }
        }
    }

    authenticate {
        post("/signup") {
            val userFromToken = call.principal<User>()
            val signUpRequestBody = call.receiveOrNull<SignUpRequest>()

            when (val result = authService.createUser(
                userFromToken?.id,
                signUpRequestBody?.username
            )) {
                is Either.Right -> call.buildCrossingSuccessResponse(result.value)
                is Either.Left -> call.buildCrossingFailResponse(result.error.errorMessage)
            }
        }
    }
}


