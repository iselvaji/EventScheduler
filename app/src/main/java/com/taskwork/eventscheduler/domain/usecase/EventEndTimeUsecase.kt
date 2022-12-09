package com.taskwork.eventscheduler.domain.usecase

import com.taskwork.eventscheduler.util.toTime
import java.util.*

class EventEndTimeUsecase {

    fun validate(startTime: Long?, endTime: Long?) : ValidationResult {
        if(endTime == null || startTime == null)
            return ValidationResult(
                successful = false,
                error = ValidationError.RequiredField)

        val startTimeStr = startTime.toTime().split(":")
        val endTimeStr = endTime.toTime().split(":")

        val timeToMatchStartTime = Calendar.getInstance()
        timeToMatchStartTime[Calendar.HOUR] = startTimeStr[0].toInt()
        timeToMatchStartTime[Calendar.MINUTE] =  startTimeStr[1].toInt()

        val timeToMatchEndTime = Calendar.getInstance()
        timeToMatchEndTime[Calendar.HOUR] = endTimeStr[0].toInt()
        timeToMatchEndTime[Calendar.MINUTE] =  endTimeStr[1].toInt()

        if (timeToMatchEndTime.before(timeToMatchStartTime) ||
            timeToMatchEndTime.equals(timeToMatchStartTime))  {
            return ValidationResult(
                successful = false,
                error = ValidationError.EventScheduleError.EndTimeOlderError
            )
        }

        return ValidationResult(successful = true, error = null)
    }
}