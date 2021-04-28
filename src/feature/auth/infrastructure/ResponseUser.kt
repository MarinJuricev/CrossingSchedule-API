package com.example.feature.auth.infrastructure

import kotlinx.serialization.Serializable

@Serializable
data class ResponseUser(
    val id: String,
    val username: String,
)