package com.example.feature.schedule.infrastructure

interface ScheduleService {
    suspend fun getScheduleForGivenDay()
    suspend fun updateScheduleForGivenDay()
}