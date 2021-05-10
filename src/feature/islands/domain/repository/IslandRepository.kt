package com.example.feature.islands.domain.repository

import com.example.core.model.Either
import com.example.feature.islands.domain.model.IslandFailure
import com.example.feature.islands.domain.model.IslandInfo

interface IslandRepository {
    suspend fun createIsland(userId: String, islandInfo: IslandInfo): Either<IslandFailure, Int>
    suspend fun getIslandById(islandId: Int): Either<IslandFailure, IslandInfo>
    suspend fun getIslandsForGivenUser(userId: String): Either<IslandFailure, List<IslandInfo>>
}