package com.taskwork.eventscheduler.domain.usecase

import com.taskwork.eventscheduler.domain.model.EventInfo
import com.taskwork.eventscheduler.domain.repository.Repository
import com.taskwork.eventscheduler.util.TimeUtil
import com.taskwork.eventscheduler.util.toDate
import kotlinx.coroutines.flow.collectLatest

class EventSaveUsecase(private val repository: Repository) {

    suspend fun schedule(eventInfo: EventInfo) : ValidationResult {

        var isOverLapping = false
        repository.getEvents().collectLatest {
            val eventsByDate = it.groupBy { eventInfo.date.toDate() }

            eventsByDate.forEach { (eventDate, events) ->
                if(eventDate == eventInfo.date.toDate()) {
                    events.forEach { event ->
                        isOverLapping = TimeUtil.isOverlapping(
                            event.startTime,
                            event.endTime,
                            eventInfo.startTime,
                            eventInfo.endTime)

                        if(isOverLapping)
                            return@forEach
                    }
                }
            }
        }

        if(isOverLapping) {
            return ValidationResult(
                successful = false,
                error = ValidationError.EventScheduleError.TimeOverlappingError
            )
        }

        repository.insertEvent(eventInfo)

        return ValidationResult(
            successful = true,
            error = null
        )
    }
}