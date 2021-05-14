package com.example.feature.islands.domain.mapper

import com.example.core.model.Mapper
import com.example.feature.islands.domain.model.IslandRequestInfo
import com.example.feature.islands.infrastructure.model.IslandCreationRequest

class IslandCreationRequestToIslandRequestInfoMapper : Mapper<IslandRequestInfo, IslandCreationRequest> {

    override suspend fun map(
        origin: IslandCreationRequest
    ): IslandRequestInfo {
        return with(origin){
            IslandRequestInfo(
                islandName = islandName,
                hemisphere = hemisphere,
                numberOfVillagers = numberOfVillagers,
            )
        }
    }
}