package com.taskwork.eventscheduler.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EventStartTimeUsecaseTest {

    private lateinit var usecase: EventStartTimeUsecase

    @Before
    fun setUp() {
        usecase = EventStartTimeUsecase()
    }

    @Test
    fun `check event start time validation result with valid event start time`() {

        // given
        val expected = ValidationResult(successful = true, error = null)

        // when
        val result = usecase.validate(System.currentTimeMillis(), System.currentTimeMillis())

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `check event start time validation result with invalid event start time`() {

        // given
        val expected = ValidationResult(successful = false, error =  ValidationError.RequiredField)

        // when
        val result = usecase.validate(null, null)

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `check event start time validation result with invalid past start time`() {

        // given
        val expected = ValidationResult(successful = false, error = ValidationError.EventScheduleError.PastTimeError)

        // when
        val result = usecase.validate(System.currentTimeMillis() - 60000, System.currentTimeMillis())

        // then
        assertEquals(expected, result)
    }

}