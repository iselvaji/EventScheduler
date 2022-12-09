package com.taskwork.eventscheduler.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DBConstants.TABLE_EVENT)
data class EventEntity(

   /* @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DBConstants.COLUMN_ID)
    var id: Int? = null,*/

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = DBConstants.COLUMN_EVENT_NAME)
    var name: String = "",

    @ColumnInfo(name = DBConstants.COLUMN_EVENT_DATE)
    var date: Long = 0L,

    @ColumnInfo(name = DBConstants.COLUMN_EVENT_START_TIME)
    var startTime: Long = 0L,

    @ColumnInfo(name = DBConstants.COLUMN_EVENT_END_TIME)
    var endTime: Long = 0L,
)