package com.taskwork.eventscheduler.domain.usecase

import com.taskwork.eventscheduler.util.TimeUtil.isToday
import com.taskwork.eventscheduler.util.toTime
import java.util.*

class EventStartTimeUsecase {

    fun validate(startTime: Long?, date: Long?) : ValidationResult {

        if(startTime == null)
            return ValidationResult(
                successful = false,
                error = ValidationError.RequiredField
            )

        val startTimeStr = startTime.toTime().split(":")
        val currentTime = Calendar.getInstance()

        val timeToMatchStartTime = Calendar.getInstance()
        timeToMatchStartTime[Calendar.HOUR] = startTimeStr[0].toInt()
        timeToMatchStartTime[Calendar.MINUTE] =  startTimeStr[1].toInt()

        val isToday = date?.let { isToday(it) }

        if(isToday == true && timeToMatchStartTime.before(currentTime) ) {
            return ValidationResult(
                successful = false,
                error = ValidationError.EventScheduleError.PastTimeError
            )
        }
        return ValidationResult(successful = true, error = null)
    }
}