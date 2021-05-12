package com.example.feature.islands.data.repository

import com.example.core.model.Either
import com.example.core.model.Failure
import com.example.core.model.buildLeft
import com.example.core.model.buildRight
import com.example.feature.islands.data.dao.IslandDao
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.domain.model.IslandRequestInfo
import com.example.feature.islands.domain.repository.IslandRepository

class IslandRepositoryImpl(
    private val islandDao: IslandDao,
) : IslandRepository {

    override suspend fun createIsland(
        userId: String,
        islandRequestInfo: IslandRequestInfo
    ): Either<Failure, Int> =
        islandDao.createIsland(userId, islandRequestInfo).buildRight()

    override suspend fun getIslandById(
        islandId: Int,
    ): Either<Failure, IslandInfo> {
        val islandInfo = islandDao.getIslandById(islandId)

        return islandInfo?.buildRight() ?: Failure("Island for id $islandId was not found").buildLeft()
    }

    override suspend fun getIslandsForGivenUser(userId: String): Either<Failure, List<IslandInfo>> =
        islandDao.getIslandsFromUserId(userId).buildRight()
}