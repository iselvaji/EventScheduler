package com.taskwork.eventscheduler.di

import com.taskwork.eventscheduler.data.repository.RepositoryImpl
import com.taskwork.eventscheduler.domain.repository.Repository
import com.taskwork.eventscheduler.domain.usecase.EventSchedulerUsecase
import com.taskwork.eventscheduler.domain.usecase.EventsUsecase
import com.taskwork.eventscheduler.presentation.eventschedule.EventScheduleViewModel
import com.taskwork.eventscheduler.presentation.eventslist.EventsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    single {
        RepositoryImpl(get()) as Repository
    }

    viewModel {
        provideEventsListViewModel(get())
    }

    viewModel {
        provideEventsScheduleViewModel(get())
    }
}

private fun provideEventsListViewModel(useCase: EventsUsecase) =  EventsListViewModel(useCase)
private fun provideEventsScheduleViewModel(useCase: EventSchedulerUsecase) = EventScheduleViewModel(useCase)