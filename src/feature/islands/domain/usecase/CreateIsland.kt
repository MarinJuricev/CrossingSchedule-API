package com.example.feature.islands.domain.usecase

import com.example.core.model.Either
import com.example.core.model.Either.Left
import com.example.core.model.Either.Right
import com.example.core.model.Failure
import com.example.core.model.Mapper
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.domain.model.IslandRequestInfo
import com.example.feature.islands.domain.repository.IslandRepository
import com.example.feature.islands.infrastructure.model.IslandCreationRequest

class CreateIsland(
    private val islandRepository: IslandRepository,
    private val islandCreationRequestToIslandRequestInfoMapper: Mapper<IslandRequestInfo, IslandCreationRequest>
) {
    suspend operator fun invoke(
        userId: String,
        islandCreationRequest: IslandCreationRequest,
    ): Either<Failure, IslandInfo> {
        return when (val result = islandRepository.createIsland(
            userId,
            islandCreationRequestToIslandRequestInfoMapper.map(islandCreationRequest)
        )) {
            is Left -> result
            is Right -> islandRepository.getIslandById(result.value)
        }
    }
}