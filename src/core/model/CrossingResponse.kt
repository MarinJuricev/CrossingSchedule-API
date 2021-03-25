package com.example.core.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class CrossingResponse(
    val status: CrossingStatus,
    @Contextual
    val data: Any? = null,
    val message: String? = null,
)

