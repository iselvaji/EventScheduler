package com.taskwork.eventscheduler.presentation.eventslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskwork.eventscheduler.domain.model.EventInfo
import com.taskwork.eventscheduler.domain.usecase.EventsUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EventsListViewModel(
    private val useCase: EventsUsecase,
) : ViewModel(){

    private val _stateEvents = MutableStateFlow<List<EventInfo>>(emptyList())
    val stateEvents = _stateEvents.asStateFlow()


    fun onEvent(event: EventsListScreenEvent) {
        when(event) {
            is EventsListScreenEvent.FetchEvents -> {
                 getSavedEvents()
            }
        }
    }

    private fun getSavedEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.fetchData().collectLatest {
                _stateEvents.emit(it)
            }
        }
    }
}
