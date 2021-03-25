package com.example.feature.auth.data.dao

import com.example.feature.auth.domain.model.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

interface AuthDao {
    suspend fun getUserById(userId: String): User?
    suspend fun createUser(userId: String, username: String): String
}

object Users : Table(), AuthDao {
    val id = varchar("id", length = 255)
    val username = varchar("username", length = 255)
    private val creationTime = long("creationTime")

    override suspend fun getUserById(userId: String): User? = newSuspendedTransaction {
        select {
            (Users.id eq userId)
        }.mapNotNull {
            it.mapRowToUser()
        }.singleOrNull()
    }

    override suspend fun createUser(userId: String, username: String): String =
        newSuspendedTransaction {
            insert {
                it[id] = userId
                it[Users.username] = username
                it[creationTime] = System.currentTimeMillis()
            } get Users.id
        }
}

private fun ResultRow.mapRowToUser() =
    User(
        id = this[Users.id],
        username = this[Users.username],
    )