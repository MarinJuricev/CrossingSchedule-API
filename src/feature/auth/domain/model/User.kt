package com.example.feature.auth.domain.model

import io.ktor.auth.*

data class User(
    val id: String,
    val username: String
): Principal
