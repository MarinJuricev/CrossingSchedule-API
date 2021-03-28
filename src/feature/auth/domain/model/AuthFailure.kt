package com.example.feature.auth.domain.model

sealed class AuthFailure(val errorMessage: String) {
    data class NoUserPresentForTokenFailure(val error: String = "No user present for that id") : AuthFailure(error)
    data class InvalidLoginFailure(val error: String = "No user found, double check credentials") : AuthFailure(error)
    data class ErrorWhileCreatingUserAccountFailure(val error: String = "Error while creating user account") : AuthFailure(error)
    data class MissingRequiredArgument(val error: String) : AuthFailure(error)
}
