package com.example.feature.auth.ext

import com.example.feature.auth.FirebaseAuthenticationProvider
import com.google.firebase.ErrorCode
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val firebaseAuthLogger: Logger = LoggerFactory.getLogger("io.robothouse.auth.firebase")

fun Authentication.Configuration.firebase(
    name: String? = null,
    configure: FirebaseAuthenticationProvider.Configuration.() -> Unit
) {
    val provider = FirebaseAuthenticationProvider.Configuration(name).apply(configure).build()
    provider.pipeline.intercept(AuthenticationPipeline.RequestAuthentication) { context ->

        //TODO CLEANUP INTO THE FLOW
        try {
            val token = provider.token(call) ?: throw FirebaseAuthException(
                FirebaseException(
                    ErrorCode.UNAUTHENTICATED,
                    "No token could be found",
                    null
                )
            )
            val uid = FirebaseAuth.getInstance().verifyIdToken(token).uid
            provider.principle?.let {
                it.invoke(uid)?.let { principle -> context.principal(principle) }
            }
        } catch (cause: Throwable) {
            val message = if (cause is FirebaseAuthException) {
                "Authentication failed: ${cause.message ?: cause.javaClass.simpleName}"
            } else {
                cause.message ?: cause.javaClass.simpleName
            }
            firebaseAuthLogger.trace(message)
            call.respond(HttpStatusCode.Unauthorized, message)
            context.challenge.complete()
            finish()
        }
    }
    register(provider)
}

fun ApplicationRequest.parseAuthorizationToken(): String? = authorization()?.let {
    it.split(" ")[1]
}