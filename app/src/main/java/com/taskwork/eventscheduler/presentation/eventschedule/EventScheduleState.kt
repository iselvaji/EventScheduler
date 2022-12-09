package com.taskwork.eventscheduler.presentation.eventschedule

import com.taskwork.eventscheduler.domain.usecase.ValidationError

data class EventScheduleState(

    val eventName: String? = null,
    val eventStartTime: Long? = null,
    val eventEndTime: Long? = null,
    val eventDate: Long? = null,

    val eventNameError: ValidationError? = null,
    val eventStartTimeError: ValidationError? = null,
    val eventEndTimeError: ValidationError? = null,
    val eventDateError: ValidationError? = null,
    val eventOverlapError: ValidationError? = null,

    val eventCreated: Boolean? = false,
)