package com.example.feature.islands.infrastructure

import com.example.core.model.*
import com.example.core.model.Either.Left
import com.example.core.model.Either.Right
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.domain.usecase.CreateIsland
import com.example.feature.islands.domain.usecase.GetIslands
import com.example.feature.islands.infrastructure.model.IslandCreationRequest
import com.example.feature.islands.infrastructure.model.ResponseIsland

interface IslandService {
    suspend fun createIsland(userId: String?, request: IslandCreationRequest?): Either<Failure, ResponseIsland>
    suspend fun getIslandForGivenUserId(userId: String?): Either<Failure, List<ResponseIsland>>
}

class IslandServiceImpl(
    private val createIslandUseCase: CreateIsland,
    private val getIslands: GetIslands,
    private val islandInfoToResponseIslandMapper: Mapper<ResponseIsland, IslandInfo>,
) : IslandService {

    override suspend fun createIsland(
        userId: String?,
        request: IslandCreationRequest?
    ): Either<Failure, ResponseIsland> {
        if (userId == null) {
            return Failure("Invalid userId cannot be null got : $userId").buildLeft()
        } else if (request == null) {
            return Failure("Invalid request cannot be null got : $request").buildLeft()
        }

        return when (val result = createIslandUseCase(userId, request)) {
            is Left -> result
            is Right -> islandInfoToResponseIslandMapper.map(result.value).buildRight()
        }
    }

    override suspend fun getIslandForGivenUserId(
        userId: String?,
    ): Either<Failure, List<ResponseIsland>> {
        if (userId == null)
            return Failure("Invalid userId cannot be null got : $userId").buildLeft()

        return when (val result = getIslands(userId)) {
            is Left -> result
            is Right -> result.value.map { islandInfo ->
                islandInfoToResponseIslandMapper.map(islandInfo)
            }.buildRight()
        }

    }
}