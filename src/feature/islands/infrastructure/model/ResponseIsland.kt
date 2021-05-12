package com.example.feature.islands.infrastructure.model

import com.example.feature.islands.domain.model.Hemisphere

data class ResponseIsland(
    val islandId: Int,
    val islandName: String,
    val hemisphere: Hemisphere,
    val numberOfVillagers: Int,
)
