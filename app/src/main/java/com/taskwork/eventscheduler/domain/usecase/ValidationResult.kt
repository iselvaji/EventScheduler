package com.taskwork.eventscheduler.domain.usecase

data class ValidationResult(val successful: Boolean, val error: ValidationError? = null)

sealed class ValidationError {
    object RequiredField : ValidationError()

    sealed class EventScheduleError: ValidationError() {
        object PastTimeError : ValidationError()
        object TimeOverlappingError : ValidationError()
        object EndTimeOlderError : ValidationError()
    }
}