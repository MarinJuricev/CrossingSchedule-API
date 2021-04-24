package com.example.feature.islands.domain.repository

import com.example.core.model.Either
import com.example.feature.islands.domain.model.IslandFailure
import com.example.feature.islands.domain.model.IslandInfo

interface IslandRepository {

    suspend fun createIsland(userId: String, islandInfo: IslandInfo): Either<IslandFailure, IslandInfo>
}