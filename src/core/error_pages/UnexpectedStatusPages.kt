package com.example.core.error_pages

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun StatusPages.Configuration.unexpectedStatusPages() {
    exception<UnknownError> {
        call.respondText(
            "Internal server error",
            ContentType.Text.Plain,
            status = HttpStatusCode.InternalServerError
        )
    }
    exception<IllegalArgumentException> {
        call.respond(HttpStatusCode.BadRequest)
    }
}