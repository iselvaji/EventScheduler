package com.taskwork.eventscheduler.data.mapper

import com.taskwork.eventscheduler.data.local.database.EventEntity
import com.taskwork.eventscheduler.domain.model.EventInfo

fun EventEntity.toEventInfo() : EventInfo {
    return EventInfo(name, date, startTime, endTime)
}

fun EventInfo.toEventEntity() : EventEntity {
    return EventEntity(name, date, startTime, endTime)
}