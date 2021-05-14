package com.example.feature.islands.di

import com.example.core.model.Mapper
import com.example.feature.islands.data.dao.IslandDao
import com.example.feature.islands.data.dao.Islands
import com.example.feature.islands.data.repository.IslandRepositoryImpl
import com.example.feature.islands.domain.mapper.IslandCreationRequestToIslandRequestInfoMapper
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.domain.model.IslandRequestInfo
import com.example.feature.islands.domain.repository.IslandRepository
import com.example.feature.islands.domain.usecase.CreateIsland
import com.example.feature.islands.infrastructure.IslandService
import com.example.feature.islands.infrastructure.IslandServiceImpl
import com.example.feature.islands.infrastructure.mapper.IslandInfoToResponseIslandMapper
import com.example.feature.islands.infrastructure.model.IslandCreationRequest
import com.example.feature.islands.infrastructure.model.ResponseIsland
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val islandInfoToResponseIslandMapperName = "IslandInfoToResponseIslandMapperName"
const val islandCreationRequestToIslandRequestInfoMapperName = "IslandCreationRequestToIslandRequestInfoMapperName"

val islandModule = module {
    single<IslandDao> { Islands }

    factory<IslandService> { IslandServiceImpl(get(), get(named(islandInfoToResponseIslandMapperName))) }
    factory<IslandRepository> { IslandRepositoryImpl(get()) }
    factory<Mapper<ResponseIsland, IslandInfo>>(named(islandInfoToResponseIslandMapperName)) {
        IslandInfoToResponseIslandMapper()
    }
    factory<Mapper<IslandRequestInfo, IslandCreationRequest>>(named(islandCreationRequestToIslandRequestInfoMapperName)) {
        IslandCreationRequestToIslandRequestInfoMapper()
    }
    factory { CreateIsland(get(), get(named(islandCreationRequestToIslandRequestInfoMapperName))) }

}