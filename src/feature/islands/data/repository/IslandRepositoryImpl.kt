package com.example.feature.islands.data.repository

import com.example.core.model.Either
import com.example.core.model.buildLeft
import com.example.core.model.buildRight
import com.example.feature.islands.data.dao.IslandDao
import com.example.feature.islands.domain.model.IslandFailure
import com.example.feature.islands.domain.model.IslandFailure.IslandNotFoundFailure
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.domain.repository.IslandRepository

class IslandRepositoryImpl(
    private val islandDao: IslandDao,
) : IslandRepository {

    override suspend fun createIsland(
        userId: String,
        islandInfo: IslandInfo,
    ): Either<IslandFailure, Int> =
        islandDao.createIsland(userId, islandInfo).buildRight()

    override suspend fun getIslandById(
        islandId: Int,
    ): Either<IslandFailure, IslandInfo> {
        val islandInfo = islandDao.getIslandById(islandId)

        return islandInfo?.buildRight() ?: IslandNotFoundFailure("Island for id $islandId was not found").buildLeft()
    }

    override suspend fun getIslandsForGivenUser(userId: String): Either<IslandFailure, List<IslandInfo>> =
        islandDao.getIslandsFromUserId(userId).buildRight()
}