package com.taskwork.eventscheduler.data.local.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class EventDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: EventDatabase
    private lateinit var dao: EventDao


    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            EventDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.eventDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertEvent() = runBlocking {

        val event =  EventEntity(name = "Test Event",
            date = System.currentTimeMillis(),
            startTime = System.currentTimeMillis(),
            endTime = System.currentTimeMillis() + 60000)

        dao.insert(event)

        val allEvents = dao.getEvents()

        assertThat(allEvents).contains(event)
    }

}