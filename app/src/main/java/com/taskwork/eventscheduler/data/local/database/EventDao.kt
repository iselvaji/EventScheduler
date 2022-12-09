package com.taskwork.eventscheduler.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: EventEntity)

    @Query("SELECT * FROM ${DBConstants.TABLE_EVENT}")
    fun getEvents(): List<EventEntity>
}