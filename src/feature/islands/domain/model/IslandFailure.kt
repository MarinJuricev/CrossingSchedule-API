package com.example.feature.islands.domain.model

sealed class IslandFailure(val errorMessage: String) {
    data class InvalidUserIdFailure(val error: String) : IslandFailure(error)
    data class IslandNotFoundFailure(val error: String) : IslandFailure(error)
}
