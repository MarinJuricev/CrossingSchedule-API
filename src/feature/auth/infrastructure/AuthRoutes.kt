package com.example.feature.auth.infrastructure

import com.example.core.model.CrossingResponse
import com.example.core.model.CrossingStatus
import com.example.core.model.Either
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

            when (val result = authService.loginUser(userFromToken?.id)) {
                is Either.Right -> {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = CrossingResponse(
                            status = CrossingStatus.Success,
                            data = result.value
                        )
                    )
                }
                is Either.Left -> {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = CrossingResponse(
                            status = CrossingStatus.Fail,
                            message = result.error.errorMessage
                        )
                    )
                }
            }
        }
    }
}


