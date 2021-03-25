package com.example.feature.auth

import com.example.feature.auth.infrastructure.AuthService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object AuthConfig : KoinComponent {

    private val authService by inject<AuthService>()

    fun FirebaseAuthenticationProvider.Configuration.configure(
    ) {
        principal = { uid ->
            authService.getUserFromAuthUid(uid)
        }
    }
}