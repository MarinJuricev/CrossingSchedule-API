package com.example.feature.islands.domain.model

data class IslandRequestInfo(
    val islandName: String,
    val hemisphere: Hemisphere,
    val numberOfVillagers: Int,
)
