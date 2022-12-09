package com.taskwork.eventscheduler.domain.usecase

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EventEndTimeUsecaseTest {

    private lateinit var usecase: EventEndTimeUsecase

    @Before
    fun setUp() {
        usecase = EventEndTimeUsecase()
    }

    @Test
    fun `check event validation result with valid event start and end time`() {

        // given
        val expected = ValidationResult(successful = true, error = null)

        // when
        val result = usecase.validate(System.currentTimeMillis(), System.currentTimeMillis() + 60000)

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `check event end time validation result with invalid event start and end time`() {

        // given
        val expected = ValidationResult(successful = false, error = ValidationError.RequiredField)

        // when
        val result = usecase.validate(null, null)

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `check event end time validation result with end time less than start time`()  = runBlocking {

        // given
        val expected = ValidationResult(successful = false, error = ValidationError.EventScheduleError.EndTimeOlderError)

        // when
        val result = usecase.validate(System.currentTimeMillis(), System.currentTimeMillis() - 60000)

        // then
        assertEquals(expected, result)
    }

}