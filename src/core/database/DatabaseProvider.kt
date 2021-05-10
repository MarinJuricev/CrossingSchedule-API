package com.example.core.database

import com.example.feature.auth.data.dao.Users
import com.example.feature.islands.data.dao.Islands
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseProviderImpl(
    private val config: Config
) : DatabaseProvider {

    override fun init() {
        Database.connect(hikari(config))
        transaction {
            create(Users)
            create(Islands)
        }
    }

    private fun hikari(mainConfig: Config): HikariDataSource {
        HikariConfig().run {
            driverClassName = "org.h2.Driver"
            jdbcUrl = "jdbc:h2:mem:test"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
            return HikariDataSource(this)
        }
//        HikariConfig().run {
//            driverClassName = "com.mysql.jdbc.Driver"
//            jdbcUrl = "jdbc:mysql://${mainConfig.databaseHost}:${mainConfig.databasePort}/${Config.DATABASENAME}"
//            username = Config.DATABASEUSER
//            password = Config.DATABASEPASSWORD
//            isAutoCommit = false
//            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
//            validate()
//            return HikariDataSource(this)
//        }
    }
}

interface DatabaseProvider {
    fun init()
}