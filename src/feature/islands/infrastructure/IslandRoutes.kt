package com.example.feature.islands.infrastructure

import com.example.feature.auth.domain.model.User
import com.example.feature.islands.infrastructure.model.IslandCreationRequest
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.registerIslandRoutes() {
    routing {
        islandCreationRoute()
    }
}

fun Route.islandCreationRoute() {

    val islandService by inject<IslandService>()

    authenticate {
        post("/island") {
            val userFromToken = call.principal<User>()
            val islandCreationBody = call.receiveOrNull<IslandCreationRequest>()

            islandService.createIsland(userFromToken?.id ,islandCreationBody)
        }
    }


}