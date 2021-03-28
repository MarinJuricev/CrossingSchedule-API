package com.example.core.ext

import com.example.core.error_pages.unexpectedStatusPages
import com.example.feature.auth.AuthConfig.configure
import com.example.feature.auth.domain.model.AuthResponse
import com.example.feature.auth.domain.model.authStatusPages
import com.example.feature.auth.ext.firebase
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

//TODO Separate actually the generic one from the ones that'll change ( looking at you install(ContentNegotiation) json and statusPages
fun Application.installCommonFeatures() {
    install(Locations)
    install(DataConversion)
    install(CallLogging)
    install(Authentication) { firebase { configure() } }
    install(DefaultHeaders)

    install(ContentNegotiation) {
        json(
            json = Json {
                encodeDefaults = true
                isLenient = true
                allowSpecialFloatingPointValues = true
                allowStructuredMapKeys = true
                prettyPrint = true
                useArrayPolymorphism = true

                serializersModule = SerializersModule {
                    contextual(AuthResponse.serializer())
                }
            }
        )
    }
    install(StatusPages) {
        authStatusPages()
        unexpectedStatusPages()
    }
}