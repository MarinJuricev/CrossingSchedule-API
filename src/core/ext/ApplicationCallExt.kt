package com.example.core.ext

import com.example.core.model.CrossingResponse
import com.example.core.model.CrossingStatus
import com.example.core.model.Either
import com.example.core.model.Either.Left
import com.example.core.model.Either.Right
import com.example.core.model.Failure
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*

suspend fun ApplicationCall.sendEither(
    either: Either<Failure, Any>
) {
    when (either) {
        is Left -> buildCrossingFailResponse(either.error.errorMessage)
        is Right -> buildCrossingSuccessResponse(either.value)
    }
}

suspend fun ApplicationCall.buildCrossingSuccessResponse(
    data: Any,
    httpStatusCode: HttpStatusCode = HttpStatusCode.OK,
) {
    respond(
        status = httpStatusCode,
        message = CrossingResponse(
            status = CrossingStatus.Success,
            data = data
        )
    )
}

suspend fun ApplicationCall.buildCrossingFailResponse(
    errorMessage: String,
    httpStatusCode: HttpStatusCode = HttpStatusCode.BadRequest,
) {
    respond(
        status = httpStatusCode,
        message = CrossingResponse(
            status = CrossingStatus.Fail,
            message = errorMessage
        )
    )
}