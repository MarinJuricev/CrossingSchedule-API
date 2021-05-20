package com.example.feature.auth.infrastructure

import com.example.core.ext.sendEither
import com.example.feature.auth.domain.model.User
import com.example.feature.auth.infrastructure.model.SignUpRequest
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.registerAuthRoutes() {

    val authService by inject<AuthService>()

    routing {
        loginRoute(authService)
        signUpRoute(authService)
    }
}

fun Route.loginRoute(authService: AuthService) {

    authenticate {
        get("/login") {
            val userFromToken = call.principal<User>()

            call.sendEither(authService.loginUser(userFromToken?.id))
        }
    }
}

fun Route.signUpRoute(authService: AuthService) {

    authenticate {
        post("/signup") {
            val userFromToken = call.principal<User>()
            val signUpRequestBody = call.receiveOrNull<SignUpRequest>()

            call.sendEither(
                authService.createUser(
                    userFromToken?.id,
                    signUpRequestBody?.username
                )
            )
        }
    }
}


