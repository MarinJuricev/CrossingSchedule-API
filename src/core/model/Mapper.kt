package com.example.core.model

interface Mapper<R, O> {
    suspend fun map(origin: O): R
}