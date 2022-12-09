package com.taskwork.eventscheduler

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import com.taskwork.eventscheduler.di.AppModule
import com.taskwork.eventscheduler.di.UsecaseModule
import com.taskwork.eventscheduler.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            androidContext(appContext)
            loadKoinModules(listOf(AppModule, databaseModule, UsecaseModule))
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}
