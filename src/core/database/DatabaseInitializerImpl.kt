package com.example.core.database

import com.example.feature.auth.FirebaseAdmin

fun interface DatabaseInitializer {
    fun init()
}

class DatabaseInitializerImpl(
    private val databaseProvider: DatabaseProvider,
    private val firebaseAdmin: FirebaseAdmin,
) : DatabaseInitializer {

    override fun init() {
        firebaseAdmin.init()
        databaseProvider.init()
    }
}