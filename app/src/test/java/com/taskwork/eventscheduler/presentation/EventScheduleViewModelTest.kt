package com.taskwork.eventscheduler.presentation

import app.cash.turbine.test
import com.taskwork.eventscheduler.domain.usecase.*
import com.taskwork.eventscheduler.presentation.eventschedule.EventScheduleViewModel
import com.taskwork.eventscheduler.presentation.eventschedule.EventsScheduleScreenEvent
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EventScheduleViewModelTest {

    @get:Rule
    val dispatcherRule = ViewModelRule()

    @MockK
    private lateinit var usecase: EventSaveUsecase

    private lateinit var usecaseScheduler : EventSchedulerUsecase

    @MockK
    private lateinit var usecaseName : EventNameUsecase

    @MockK
    private lateinit var usecaseStartTime : EventStartTimeUsecase

    @MockK
    private lateinit var usecaseEndTime : EventEndTimeUsecase

    @MockK
    private lateinit var usecaseDate : EventDateUsecase

    private lateinit var viewModel: EventScheduleViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        usecase = mockk()
        usecaseName = mockk()
        usecaseStartTime = mockk()
        usecaseEndTime = mockk()
        usecaseDate = mockk()
        usecaseScheduler = EventSchedulerUsecase(usecaseDate,
            usecaseStartTime,
            usecaseEndTime,
            usecaseName,
            usecase)
    }

    @Test
    fun `view model schedule success state verify`() = runBlocking {

        // given
        val result = ValidationResult(successful = true, error = null)

        coEvery {
            usecase.schedule(any())
        } returns result

        coEvery {
            usecaseName.validate(any())
        } returns result

        coEvery {
            usecaseDate.validate(any())
        } returns result

        coEvery {
            usecaseStartTime.validate(any(), any())
        } returns result

        coEvery {
            usecaseEndTime.validate(any(), any())
        } returns result

        // when
        viewModel = EventScheduleViewModel(usecaseScheduler)

        viewModel.onEvent(EventsScheduleScreenEvent.EventNameChanged(name = "Test"))
        viewModel.onEvent(EventsScheduleScreenEvent.EventDateChanged(eventDate = System.currentTimeMillis()))
        viewModel.onEvent(EventsScheduleScreenEvent.StartTimeChanged(startTime = System.currentTimeMillis()))
        viewModel.onEvent(EventsScheduleScreenEvent.EndTimeChanged(endTime = System.currentTimeMillis()))

        viewModel.onEvent(EventsScheduleScreenEvent.ScheduleNewEvent)

        viewModel.sharedFlow.test {
            assert(awaitItem())
        }
    }
}