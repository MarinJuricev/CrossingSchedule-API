package com.example.feature.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val message: String
)
