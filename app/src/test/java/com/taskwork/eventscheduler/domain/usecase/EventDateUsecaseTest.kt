package com.taskwork.eventscheduler.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EventDateUsecaseTest {

    private lateinit var usecase: EventDateUsecase

    @Before
    fun setUp() {
        usecase = EventDateUsecase()
    }

    @Test
    fun `check event date validation result with valid event date`() {

        // given
        val expected = ValidationResult(successful = true, error = null)

        // when
        val result = usecase.validate(System.currentTimeMillis())

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `check event date validation result with invalid event date`() {

        // given
        val expected = ValidationResult(successful = false, error =  ValidationError.RequiredField)

        // when
        val result = usecase.validate(null)

        // then
        assertEquals(expected, result)
    }

}