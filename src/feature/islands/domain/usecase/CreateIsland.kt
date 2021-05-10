package com.example.feature.islands.domain.usecase

import com.example.core.model.Either
import com.example.core.model.Either.Left
import com.example.core.model.Either.Right
import com.example.feature.islands.domain.model.IslandFailure
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.domain.repository.IslandRepository

class CreateIsland(
    private val islandRepository: IslandRepository,
) {
    suspend operator fun invoke(
        userId: String,
        islandInfo: IslandInfo,
    ): Either<IslandFailure, IslandInfo> {
        return when (val result = islandRepository.createIsland(userId, islandInfo)) {
            is Right -> islandRepository.getIslandById(result.value)
            is Left -> result
        }
    }
}