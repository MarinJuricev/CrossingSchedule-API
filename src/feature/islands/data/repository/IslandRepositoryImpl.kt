package com.example.feature.islands.data.repository

import com.example.core.model.Either
import com.example.feature.islands.data.dao.IslandDao
import com.example.feature.islands.domain.model.IslandFailure
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.domain.repository.IslandRepository

class IslandRepositoryImpl(
    private val islandDao: IslandDao,
) : IslandRepository {

    override suspend fun createIsland(
        userId: String,
        islandInfo: IslandInfo,
    ): Either<IslandFailure, IslandInfo> {
        TODO()
    }

    override suspend fun getIslandsForGivenUser(userId: String): Either<IslandFailure, List<IslandInfo>> {
        TODO()
    }
}