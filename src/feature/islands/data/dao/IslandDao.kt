package com.example.feature.islands.data.dao

import com.example.feature.auth.data.dao.Users
import com.example.feature.islands.domain.model.Hemisphere
import com.example.feature.islands.domain.model.IslandInfo
import com.example.feature.islands.domain.model.IslandRequestInfo
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

interface IslandDao {
    suspend fun createIsland(userId: String, islandRequestInfo: IslandRequestInfo): Int
    suspend fun getIslandsFromUserId(userId: String): List<IslandInfo>
    suspend fun getIslandById(islandId: Int): IslandInfo?
}

object Islands : Table(), IslandDao {
    val id: Column<Int> = integer("id").autoIncrement()
    val userId: Column<String> = (Users.varchar("userId", length = 255).references(Users.id))
    private val name: Column<String> = varchar("name", length = 255)
    private val hemisphere: Column<Hemisphere> = enumeration("hemisphere", Hemisphere::class)
    private val numberOfVillagers: Column<Int> = integer("numberOfVillagers")
    private val lastVisited: Column<Long> = long("lastVisited")

    override suspend fun createIsland(
        userId: String,
        islandRequestInfo: IslandRequestInfo,
    ): Int = newSuspendedTransaction {
        insert {
            it[name] = islandRequestInfo.islandName
            it[hemisphere] = islandRequestInfo.hemisphere
            it[numberOfVillagers] = islandRequestInfo.numberOfVillagers
            it[Islands.userId] = userId
            it[lastVisited] = System.currentTimeMillis()
        } get Islands.id
    }

    override suspend fun getIslandsFromUserId(
        userId: String,
    ): List<IslandInfo> = newSuspendedTransaction {
        select {
            (Islands.userId eq userId)
        }.mapNotNull {
            it.mapRowToIsland()
        }
    }

    override suspend fun getIslandById(islandId: Int): IslandInfo? =
        newSuspendedTransaction {
            select {
                (Islands.id eq islandId)
            }.mapNotNull {
                it.mapRowToIsland()
            }.singleOrNull()
        }

    private fun ResultRow.mapRowToIsland() =
        IslandInfo(
            islandId = this[id],
            islandName = this[name],
            hemisphere = this[hemisphere],
            numberOfVillagers = this[numberOfVillagers],
            lastVisited = this[lastVisited],
        )
}