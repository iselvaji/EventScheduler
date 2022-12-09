package com.taskwork.eventscheduler.domain.repository

import com.taskwork.eventscheduler.domain.model.EventInfo
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getEvents() : Flow<List<EventInfo>>
    suspend fun insertEvent(eventInfo: EventInfo)
}