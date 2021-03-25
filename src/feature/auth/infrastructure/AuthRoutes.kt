package com.example.feature.auth.infrastructure

import com.example.core.model.CrossingResponse
import com.example.core.model.CrossingStatus
import com.example.feature.auth.domain.model.AuthResponse
import com.example.feature.auth.domain.model.User
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
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

            call.respond(
                HttpStatusCode.OK,
                CrossingResponse(
                    status = CrossingStatus.Success, //TODO Please don't use strings... use a enum or something
                    data = AuthResponse("Successfully created user with username: ${call.principal<User>()?.username}")
                )
            )
        }
    }
}