package com.taskwork.eventscheduler.domain.usecase

import com.taskwork.eventscheduler.data.repository.TestRepository
import com.taskwork.eventscheduler.domain.model.EventInfo
import com.taskwork.eventscheduler.domain.repository.Repository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EventScheduleUsecaseTest {

    private lateinit var repo: Repository
    private lateinit var usecase: EventSaveUsecase

    @Before
    fun setUp() {
        repo = TestRepository()
        usecase = EventSaveUsecase(repo)
    }

    @Test
    fun `check event validation result success`() = runBlocking {

        // given
        val expected = ValidationResult(successful = true, error = null)

        // when
        val result = usecase.schedule(EventInfo(name = "Test Event",
                                        date = System.currentTimeMillis(),
                                        startTime = System.currentTimeMillis(),
                                        endTime = System.currentTimeMillis() + 60000))

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `check event validation event overlapping error`() = runBlocking {

        // given
        val expected = ValidationResult(successful = false, error = ValidationError.EventScheduleError.TimeOverlappingError)

        // when
        val result = usecase.schedule(
            EventInfo(name = "Test Event",
                date = System.currentTimeMillis(),
                startTime = System.currentTimeMillis(),
                endTime = System.currentTimeMillis() + 60000))

        // then
        assertEquals(expected, result)
    }

}