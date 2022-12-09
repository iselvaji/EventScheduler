package com.taskwork.eventscheduler.domain.usecase

data class EventSchedulerUsecase(
    val eventDateUsecase: EventDateUsecase,
    val eventStartTimeUsecase: EventStartTimeUsecase,
    val eventEndTimeUsecase: EventEndTimeUsecase,
    val eventNameUsecase: EventNameUsecase,
    val eventSaveUsecase: EventSaveUsecase
)
