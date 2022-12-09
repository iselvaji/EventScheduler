package com.taskwork.eventscheduler.di

import com.taskwork.eventscheduler.domain.usecase.*
import org.koin.dsl.module

val UsecaseModule = module {

    single {
        EventsUsecase(get())
    }

    single {
        EventSaveUsecase(get())
    }

    single {
        EventNameUsecase()
    }

    single {
        EventDateUsecase()
    }

    single {
        EventStartTimeUsecase()
    }

    single {
        EventEndTimeUsecase()
    }

    single {
        EventSchedulerUsecase(get(), get(), get(), get(), get())
    }
}