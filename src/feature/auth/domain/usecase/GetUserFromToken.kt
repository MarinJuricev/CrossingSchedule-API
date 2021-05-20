package com.example.feature.auth.domain.usecase

import com.example.core.model.Either
import com.example.core.model.Failure
import com.example.core.model.buildLeft
import com.example.core.model.buildRight
import com.example.feature.auth.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class GetUserFromToken(
    private val firebaseAuth: FirebaseAuth
) {
    operator fun invoke(uid: String): Either<Failure, User> {
        return try {
            firebaseAuth.getUser(uid).run {
                User(
                    id = uid,
                    username = displayName ?: "Anonymous"
                ).buildRight()
            }
        } catch (exception: FirebaseAuthException) {
            Failure("No user present for that id").buildLeft()
        }
    }
}