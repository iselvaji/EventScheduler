package com.taskwork.eventscheduler.data.repository

import com.taskwork.eventscheduler.data.local.database.EventDao
import com.taskwork.eventscheduler.data.mapper.toEventEntity
import com.taskwork.eventscheduler.data.mapper.toEventInfo
import com.taskwork.eventscheduler.domain.model.EventInfo
import com.taskwork.eventscheduler.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(private val dao: EventDao) : Repository {

    override suspend fun getEvents(): Flow<List<EventInfo>> {
        val events = dao.getEvents()
        val eventInfoList = events.map {
            it.toEventInfo()
        }
        return flow {
            emit(eventInfoList)
        }
    }

    override suspend fun insertEvent(eventInfo: EventInfo) {
        dao.insert(eventInfo.toEventEntity())
    }
}