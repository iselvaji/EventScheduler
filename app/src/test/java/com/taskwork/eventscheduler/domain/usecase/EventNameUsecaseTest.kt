package com.taskwork.eventscheduler.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EventNameUsecaseTest {

    private lateinit var usecase: EventNameUsecase

    @Before
    fun setUp() {
        usecase = EventNameUsecase()
    }

    @Test
    fun `check event name validation result with valid event name`() {

        // given
        val expected = ValidationResult(successful = true, error = null)

        // when
        val result = usecase.validate("TestEvent")

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `check event date validation result with invalid event name null`() {

        // given
        val expected = ValidationResult(successful = false, error =  ValidationError.RequiredField)

        // when
        val result = usecase.validate(null)

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `check event date validation result with invalid event name empty`() {

        // given
        val expected = ValidationResult(successful = false, error =  ValidationError.RequiredField)

        // when
        val result = usecase.validate("")

        // then
        assertEquals(expected, result)
    }

}