package com.example

import com.example.core.database.DatabaseInitializer
import com.example.core.di.coreModule
import com.example.core.ext.installCommonFeatures
import com.example.feature.auth.di.authModule
import com.example.feature.auth.infrastructure.registerAuthRoutes
import com.example.feature.schedule.infrastructure.registerScheduleRoutes
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Koin) {
        modules(
            coreModule,
            authModule
        )
    }

    installCommonFeatures()
    val databaseInitializer: DatabaseInitializer by inject()
    databaseInitializer.init()

    routing {
        registerScheduleRoutes()
        registerAuthRoutes()

        //TODO Render an actual landing page
        get("/") {
            call.respond(
                HttpStatusCode.OK,
                "Crossing schedule be"
            )
        }
    }
}

