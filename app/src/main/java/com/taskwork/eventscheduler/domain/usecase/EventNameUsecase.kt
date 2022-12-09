package com.taskwork.eventscheduler.domain.usecase

class EventNameUsecase {

    fun validate(eventName: String?) : ValidationResult {

        if(eventName.isNullOrEmpty())
            return ValidationResult(
                successful = false,
                error = ValidationError.RequiredField
            )
         return ValidationResult(successful = true, error = null)
    }
}