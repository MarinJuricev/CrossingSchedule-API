package com.example.feature.islands.infrastructure.mapper

import com.example.core.model.Mapper
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.infrastructure.model.ResponseIsland

class IslandInfoToResponseIslandMapper : Mapper<ResponseIsland, IslandInfo> {

    override suspend fun map(origin: IslandInfo): ResponseIsland {
        return with(origin) {
            ResponseIsland(
                islandId = islandId,
                islandName = islandName,
                hemisphere = hemisphere,
                numberOfVillagers = numberOfVillagers,
            )
        }
    }
}