package com.example.core.ext

import com.example.core.error_pages.unexpectedStatusPages
import com.example.feature.auth.AuthConfig.configure
import com.example.feature.auth.domain.model.AuthResponse
import com.example.feature.auth.domain.model.authStatusPages
import com.example.feature.auth.ext.firebase
import com.example.feature.auth.infrastructure.ResponseUser
import com.example.feature.islands.infrastructure.model.ResponseIsland
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
                    contextual(ResponseUser.serializer())
                    contextual(ResponseIsland.serializer())
                }
            }
        )
    }
    install(StatusPages) {
        //TODO Remove this logic, this is only needed if something that I don't have control throws a exception,
        //TODO we'd like to wrap it into our either and [CrossingResponse]
        authStatusPages()
        unexpectedStatusPages()
    }
}