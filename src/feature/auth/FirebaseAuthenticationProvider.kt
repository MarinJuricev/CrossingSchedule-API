package com.example.feature.auth

import com.example.feature.auth.ext.parseAuthorizationToken
import io.ktor.application.*
import io.ktor.auth.*

class FirebaseAuthenticationProvider internal constructor(config: Configuration) : AuthenticationProvider(config) {

    internal val token: (ApplicationCall) -> String? = config.token
    internal val principle: (suspend (uid: String) -> Principal?)? = config.principal

    class Configuration internal constructor(name: String?) : AuthenticationProvider.Configuration(name) {

        internal var token: (ApplicationCall) -> String? = { call -> call.request.parseAuthorizationToken() }

        internal var principal: (suspend (uid: String) -> Principal?)? = null

        internal fun build() = FirebaseAuthenticationProvider(this)
    }
}

