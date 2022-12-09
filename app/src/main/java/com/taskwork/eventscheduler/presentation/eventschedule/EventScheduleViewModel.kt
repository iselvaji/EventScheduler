package com.taskwork.eventscheduler.presentation.eventschedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskwork.eventscheduler.domain.model.EventInfo
import com.taskwork.eventscheduler.domain.usecase.EventSchedulerUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EventScheduleViewModel (
    private val usecase: EventSchedulerUsecase,
) : ViewModel() {

    private val _state = MutableStateFlow(EventScheduleState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<Boolean>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun onEvent(event: EventsScheduleScreenEvent) {
        when(event) {
            is EventsScheduleScreenEvent.EventNameChanged -> {
                _state.update {
                    it.copy(eventName = event.name)
                }
            }
            is EventsScheduleScreenEvent.EventDateChanged -> {
                _state.update {
                    it.copy(eventDate = event.eventDate)
                }
            }
            is EventsScheduleScreenEvent.StartTimeChanged -> {
                _state.update {
                    it.copy(eventStartTime = event.startTime)
                }
            }
            is EventsScheduleScreenEvent.EndTimeChanged -> {
                _state.update {
                    it.copy(eventEndTime = event.endTime)
                }
            }
            is EventsScheduleScreenEvent.ScheduleNewEvent -> {
                viewModelScope.launch (Dispatchers.IO){
                    validateEventInput()
                }
            }
            is EventsScheduleScreenEvent.ScheduleNewEventSuccess -> {
                _state.update {
                    it.copy(eventCreated = null)
                }
            }
        }
    }

    private suspend fun validateEventInput() {

        usecase.apply {

            val eventNameValidationResult = eventNameUsecase.validate(state.value.eventName)

            val eventDateValidationResult = eventDateUsecase.validate(state.value.eventDate)

            val eventStartTimeValidationResult = eventStartTimeUsecase.validate(
                state.value.eventStartTime,
                state.value.eventDate
            )

            val eventEndTimeValidationResult = eventEndTimeUsecase.validate(
                state.value.eventStartTime,
                state.value.eventEndTime
            )

            val hasError = listOf(eventNameValidationResult, eventDateValidationResult, eventStartTimeValidationResult, eventEndTimeValidationResult).any { !it.successful}

            if(hasError) {
                _state.update {
                    it.copy(
                        eventNameError = eventNameValidationResult.error,
                        eventDateError = eventDateValidationResult.error,
                        eventStartTimeError = eventStartTimeValidationResult.error,
                        eventEndTimeError = eventEndTimeValidationResult.error
                    )
                }
                return
            }

            val eventScheduleResult = eventSaveUsecase.schedule(eventInfo =
            EventInfo(
                name = state.value.eventName ?: "",
                date = state.value.eventDate ?: 0L,
                startTime = state.value.eventStartTime ?: 0L,
                endTime = state.value.eventEndTime ?: 0L)
            )

            if(!eventScheduleResult.successful) {
                _state.update {
                    it.copy(eventOverlapError = eventScheduleResult.error)
                }
                _sharedFlow.emit(false)
            } else
                _sharedFlow.emit(true)
        }
    }
}