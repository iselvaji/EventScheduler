package com.taskwork.eventscheduler.domain.usecase

import com.taskwork.eventscheduler.domain.model.EventInfo
import com.taskwork.eventscheduler.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class EventsUsecase(private val repository: Repository) {

    suspend fun fetchData() : Flow<List<EventInfo>> {
        return repository.getEvents()
    }
}