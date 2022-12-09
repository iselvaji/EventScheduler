package com.taskwork.eventscheduler.domain.model

data class EventInfo(
    val name: String = "",
    val date: Long = 0L,
    val startTime: Long = 0L,
    val endTime: Long = 0L
)
