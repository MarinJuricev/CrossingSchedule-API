package com.example.feature.islands.infrastructure

import com.example.core.ext.sendEither
import com.example.feature.auth.domain.model.User
import com.example.feature.islands.infrastructure.model.IslandCreationRequest
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.registerIslandRoutes() {

    val islandService by inject<IslandService>()


    routing {
        islandCreationRoute(islandService)
        getIslandsRoute(islandService)
    }
}

fun Route.islandCreationRoute(islandService: IslandService) {

    authenticate {
        post("/island") {
            val userFromToken = call.principal<User>()
            val islandCreationBody = call.receiveOrNull<IslandCreationRequest>()

            call.sendEither(islandService.createIsland(userFromToken?.id, islandCreationBody))
        }
    }
}

fun Route.getIslandsRoute(islandService: IslandService) {

    authenticate {
        get("/island") {
            val userFromToken = call.principal<User>()

            call.sendEither(islandService.getIslandForGivenUserId(userFromToken?.id))
        }
    }
}