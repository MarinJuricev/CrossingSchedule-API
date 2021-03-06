package com.example.feature.islands.domain.model

data class IslandInfo(
    val islandId: Int,
    val islandName: String,
    val hemisphere: Hemisphere,
    val numberOfVillagers: Int,
    val lastVisited: Long,
)
