package com.taskwork.eventscheduler.domain.usecase

class EventDateUsecase {

    fun validate(eventDate: Long?) : ValidationResult {
        if(eventDate == null)
            return ValidationResult(
                successful = false,
                error = ValidationError.RequiredField
            )
        return ValidationResult(successful = true, error = null)
    }
}