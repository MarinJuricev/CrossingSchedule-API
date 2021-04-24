package com.example.feature.islands.di

import com.example.feature.islands.domain.usecase.CreateIsland
import com.example.feature.islands.infrastructure.IslandService
import com.example.feature.islands.infrastructure.IslandServiceImpl
import org.koin.dsl.module

val islandModule = module {
    factory<IslandService> { IslandServiceImpl(get()) }

    factory { CreateIsland() }
}