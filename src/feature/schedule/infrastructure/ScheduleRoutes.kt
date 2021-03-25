package com.example.feature.schedule.infrastructure

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.registerScheduleRoutes() {
    routing {
        scheduleRoute()
    }
}

fun Route.scheduleRoute() {

    get("/schedule") {
        call.respondText("Work in progress!")
    }
}