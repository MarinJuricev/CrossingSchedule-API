package com.example.feature.islands.domain.repository

import com.example.core.model.Either
import com.example.core.model.Failure
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.domain.model.IslandRequestInfo

interface IslandRepository {
    suspend fun createIsland(userId: String, islandRequestInfo: IslandRequestInfo): Either<Failure, Int>
    suspend fun getIslandById(islandId: Int): Either<Failure, IslandInfo>
    suspend fun getIslandsForGivenUser(userId: String): Either<Failure, List<IslandInfo>>
}