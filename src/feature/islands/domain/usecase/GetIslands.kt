package com.example.feature.islands.domain.usecase

import com.example.core.model.Either
import com.example.core.model.Failure
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.domain.repository.IslandRepository

class GetIslands(
    private val islandRepository: IslandRepository,
) {
    suspend operator fun invoke(
        userId: String,
    ): Either<Failure, List<IslandInfo>> = islandRepository.getIslandsForGivenUser(userId)
}