package com.taskwork.eventscheduler.presentation.eventslist

sealed class EventsListScreenEvent {
    object FetchEvents: EventsListScreenEvent()
}

