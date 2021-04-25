package com.example.feature.auth.infrastructure.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val username: String,
)
