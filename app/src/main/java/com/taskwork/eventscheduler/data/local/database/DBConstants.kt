package com.taskwork.eventscheduler.data.local.database

class DBConstants {
    companion object {
        const val DATABASE_NAME = "event_db"
        const val TABLE_EVENT = "event"

        const val COLUMN_EVENT_NAME = "name"
        const val COLUMN_EVENT_DATE = "date"
        const val COLUMN_EVENT_START_TIME = "start_time"
        const val COLUMN_EVENT_END_TIME = "end_time"
    }
}