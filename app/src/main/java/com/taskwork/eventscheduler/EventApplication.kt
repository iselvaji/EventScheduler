package com.taskwork.eventscheduler

import android.app.Application
import com.taskwork.eventscheduler.di.AppModule
import com.taskwork.eventscheduler.di.UsecaseModule
import com.taskwork.eventscheduler.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EventApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@EventApplication)
            koin.loadModules(listOf(AppModule, databaseModule, UsecaseModule))
        }
    }
}