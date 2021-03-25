package com.example.core.di

import com.example.core.database.*
import com.example.feature.auth.FirebaseAdmin
import org.koin.dsl.module

val coreModule = module {
    single<DatabaseProvider> { DatabaseProviderImpl(get()) }
    single<DatabaseInitializer> { DatabaseInitializerImpl(get(), FirebaseAdmin) }
    //TODO Just messing around for now, but don't hard code this
    factory { Config("TEST_HOST", port = 8080, databaseHost = "databseHost", "databsePort") }
}