package com.taskwork.eventscheduler.presentation.eventschedule

sealed class EventsScheduleScreenEvent {

    data class EventNameChanged(val name: String) : EventsScheduleScreenEvent()
    data class EventDateChanged(val eventDate: Long) : EventsScheduleScreenEvent()
    data class StartTimeChanged(val startTime: Long) : EventsScheduleScreenEvent()
    data class EndTimeChanged(val endTime: Long) : EventsScheduleScreenEvent()
    object ScheduleNewEvent: EventsScheduleScreenEvent()
    object ScheduleNewEventSuccess: EventsScheduleScreenEvent()
}

