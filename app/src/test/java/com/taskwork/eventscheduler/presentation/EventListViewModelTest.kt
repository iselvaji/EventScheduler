package com.taskwork.eventscheduler.presentation

import app.cash.turbine.test
import com.taskwork.eventscheduler.domain.model.EventInfo
import com.taskwork.eventscheduler.domain.usecase.EventsUsecase
import com.taskwork.eventscheduler.presentation.eventslist.EventsListScreenEvent
import com.taskwork.eventscheduler.presentation.eventslist.EventsListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EventListViewModelTest {

    @get:Rule
    val dispatcherRule = ViewModelRule()

    @MockK
    private lateinit var usecase: EventsUsecase

    private lateinit var viewModel: EventsListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        usecase = mockk()
    }

    @Test
    fun `view model events data must be available`() = runBlocking {

        // given
        val data = flow {
            val testEvents = mutableListOf<EventInfo>()
            val eventInfo = EventInfo(name = "Test Event",
                date = System.currentTimeMillis(),
                startTime = System.currentTimeMillis(),
                endTime = System.currentTimeMillis() + 60000)

            testEvents.add(eventInfo)
            emit(testEvents)
        }

        coEvery {
            usecase.fetchData()
        } returns data

        // when
        viewModel = EventsListViewModel(usecase)

        viewModel.onEvent(EventsListScreenEvent.FetchEvents)

        viewModel.stateEvents.test {
            assert(awaitItem().isEmpty())
            assert(awaitItem().size == data.count())
        }
    }
}