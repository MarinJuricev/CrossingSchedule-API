package com.example.feature.islands.infrastructure.model

import com.example.feature.islands.domain.model.Hemisphere

data class IslandCreationRequest(
    val islandName: String,
    val hemisphere: Hemisphere,
    val numberOfVillagers: Int,
)
