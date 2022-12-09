package com.taskwork.eventscheduler.di

import android.content.Context
import androidx.room.Room
import com.taskwork.eventscheduler.data.local.database.DBConstants.Companion.DATABASE_NAME
import com.taskwork.eventscheduler.data.local.database.EventDao
import com.taskwork.eventscheduler.data.local.database.EventDatabase
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(context: Context): EventDatabase {
        return Room.databaseBuilder(context, EventDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(database: EventDatabase): EventDao {
        return database.eventDao()
    }

    single { provideDatabase(get()) }
    single { provideDao(get()) }
}