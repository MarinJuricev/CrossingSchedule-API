package com.example.feature.islands.di

import com.example.feature.islands.data.dao.IslandDao
import com.example.feature.islands.data.dao.Islands
import com.example.feature.islands.data.repository.IslandRepositoryImpl
import com.example.feature.islands.domain.repository.IslandRepository
import com.example.feature.islands.domain.usecase.CreateIsland
import com.example.feature.islands.infrastructure.IslandService
import com.example.feature.islands.infrastructure.IslandServiceImpl
import org.koin.dsl.module

val islandModule = module {
    single<IslandDao> { Islands }

    factory<IslandService> { IslandServiceImpl(get()) }
    factory<IslandRepository> { IslandRepositoryImpl(get()) }

    factory { CreateIsland(get()) }
}