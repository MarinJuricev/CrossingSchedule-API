package com.example.feature.auth.domain.model

sealed class AuthFailure(val errorMessage: String) {
    data class NoUserPresentForTokenFailure(val error: String = "No user present for that id") : AuthFailure(error)
    data class ErrorWhileCreatingUserAccount(val error: String = "Error while creating user account") : AuthFailure(error)
}
