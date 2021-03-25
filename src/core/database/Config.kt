package com.example.core.database

class Config(
    val host: String,
    val port: Int,
    val databaseHost: String,
    val databasePort: String
) {

    companion object {
        const val DATABASENAME: String = "crossingscheduledb"
        const val DATABASEUSER: String = "crossinguser"
        const val DATABASEPASSWORD: String = "crossingpassword"
    }
}