package com.example.feature.auth.domain.model

import com.example.core.model.CrossingResponse
import com.example.core.model.CrossingStatus
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun StatusPages.Configuration.authStatusPages() {
    exception<NoUserPresentException> { cause ->
        call.respond(
            HttpStatusCode.BadRequest,
            CrossingResponse(
                status = CrossingStatus.Fail,
                message = cause.localizedMessage
            )
        )
    }
    exception<InvalidUserException> { cause ->
        call.respond(HttpStatusCode.BadRequest, cause.localizedMessage)
    }
}

data class NoUserPresentException(override val message: String = "No user present for that id") : Exception()
data class InvalidUserException(override val message: String = "Invalid user") : Exception()