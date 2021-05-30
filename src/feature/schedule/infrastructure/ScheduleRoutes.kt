package com.example.feature.schedule.infrastructure

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.registerScheduleRoutes() {

    val scheduleService by inject<ScheduleService>()

    routing {
        getScheduleRoute(scheduleService)
        postScheduleRoute(scheduleService)
    }
}

fun Route.getScheduleRoute(scheduleService: ScheduleService) {

    get("/schedule") {
        call.respondText("Work in progress!")
    }
}

fun Route.postScheduleRoute(scheduleService: ScheduleService) {


    post("/schedule") {
        call.respondText("Work in progress!")
    }
}