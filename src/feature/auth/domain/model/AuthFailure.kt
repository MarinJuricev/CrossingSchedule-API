package com.example.feature.auth.domain.model

class AuthFailure {
    companion object {
        const val NO_USER_PRESENT_FOR_TOKEN = "No user present for that id"
        const val INVALID_LOGIN = "No user found, double check credentials"
        const val ERROR_WHILE_CREATING_USER_ACCOUNT = "Error while creating user account"
    }
}