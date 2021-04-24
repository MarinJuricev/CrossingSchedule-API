package com.example.feature.islands.infrastructure

import com.example.core.model.Either
import com.example.core.model.buildLeft
import com.example.feature.islands.domain.model.IslandFailure
import com.example.feature.islands.domain.usecase.CreateIsland
import com.example.feature.islands.infrastructure.model.IslandCreationRequest
import com.example.feature.islands.infrastructure.model.ResponseIsland

interface IslandService {
    suspend fun createIsland(userId: String?, request: IslandCreationRequest?): Either<IslandFailure, ResponseIsland>
}

class IslandServiceImpl(
    private val createIsland: CreateIsland
) : IslandService {

    override suspend fun createIsland(
        userId: String?,
        request: IslandCreationRequest?
    ): Either<IslandFailure, ResponseIsland> {
        return IslandFailure.InvalidUserIdFailure("Error").buildLeft()
    }
}