package com.taskwork.eventscheduler

import com.taskwork.eventscheduler.domain.model.EventInfo
import com.taskwork.eventscheduler.domain.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestRepository : Repository {

    override suspend fun getEvents(): Flow<List<EventInfo>> {
        return flow {
            delay(100L)

            val testData = mutableListOf<EventInfo>()

            val eventInfo = EventInfo(name = "Test Event",
                date = System.currentTimeMillis(),
                startTime = System.currentTimeMillis(),
                endTime = System.currentTimeMillis() + 60000)

            testData.add(eventInfo)

            emit(testData)
        }
    }

    override suspend fun insertEvent(eventInfo: EventInfo) {

    }

}