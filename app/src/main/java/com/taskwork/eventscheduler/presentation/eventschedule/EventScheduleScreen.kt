package com.taskwork.eventscheduler.presentation.eventschedule

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taskwork.eventscheduler.R
import com.taskwork.eventscheduler.domain.usecase.ValidationError
import com.taskwork.eventscheduler.presentation.common.UiUtil
import com.taskwork.eventscheduler.presentation.common.UiUtil.TimePicker
import com.taskwork.eventscheduler.util.Constants.TEST_TAG_EVENT_CREATE
import com.taskwork.eventscheduler.util.toDate
import com.taskwork.eventscheduler.util.toTimeMeridiem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EventScheduleScreen(viewModel : EventScheduleViewModel,
                        onNextClick: () -> Unit) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {

         viewModel.sharedFlow.collectLatest {
             if(it) {
                 onNextClick()
                 Log.d("EventCreated..", "Event created....")
             } else {
                 Log.d("EventCreated..", "Event not created....")
             }
        }
    }

    Scaffold(
        topBar = { TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            navigationIcon = {
                IconButton(onClick = onNextClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.app_name),
                    )
                }
            }
        ) }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SchedulerUI(
                viewModel = viewModel,
                state = state,
                onClick = {
                    viewModel.onEvent(EventsScheduleScreenEvent.ScheduleNewEvent)
                }
            )
        }

    }
}


@Composable
fun SchedulerUI(
    viewModel : EventScheduleViewModel,
    state: EventScheduleState,
    onClick: () -> Unit
){
    val openStartTimeDialog = remember { mutableStateOf(false) }
    val openEndTimeDialog = remember { mutableStateOf(false) }
    val openDateDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.eventName ?: "",
            onValueChange = {
                viewModel.onEvent(EventsScheduleScreenEvent.EventNameChanged(it)) },
            label = { Text(stringResource(R.string.name)) }
        )

        if (state.eventNameError != null) {
            Text(
                text = getErrorMessage(state.eventNameError, LocalContext.current),
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    openDateDialog.value = true
                },
            enabled = false,
            value = state.eventDate?.toDate() ?: "",
            onValueChange = {},
            label = { Text(stringResource(R.string.date)) }
        )

        if (state.eventDateError != null) {
            Text(
                text = getErrorMessage(state.eventDateError, LocalContext.current),
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    openStartTimeDialog.value = true
                },
            enabled = false,
            value = state.eventStartTime?.toTimeMeridiem() ?: "",
            onValueChange = {},
            label = { Text(stringResource(R.string.startTime)) }
        )

        if (state.eventStartTimeError != null) {
            Text(
                text = getErrorMessage(state.eventStartTimeError, LocalContext.current),
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    openEndTimeDialog.value = true
                },
            enabled = false,
            value = state.eventEndTime?.toTimeMeridiem() ?: "",
            onValueChange = {},
            label = { Text(stringResource(R.string.endTime)) }
        )

        if (state.eventEndTimeError != null) {
            Text(
                text = getErrorMessage(state.eventEndTimeError, LocalContext.current),
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Button(onClick = onClick,
            Modifier.padding(30.dp).testTag(TEST_TAG_EVENT_CREATE),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))) {
            Text(text = stringResource(R.string.create), color = Color.White)
        }

        if (state.eventOverlapError != null) {
            Text(
                text = getErrorMessage(state.eventOverlapError, LocalContext.current),
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }

    if(openStartTimeDialog.value) {
        TimePicker(openStartTimeDialog,
            onTimePicked = { selectedTimeInMs ->
                viewModel.onEvent(EventsScheduleScreenEvent.StartTimeChanged(selectedTimeInMs))
            })
    }

    if(openEndTimeDialog.value) {
        TimePicker(openEndTimeDialog,
            onTimePicked = { selectedTimeInMs ->
                viewModel.onEvent(EventsScheduleScreenEvent.EndTimeChanged(selectedTimeInMs))
            })
    }

    if(openDateDialog.value) {
        UiUtil.DatePicker(openDateDialog,
            onDatePicked = { selectedDate ->
                viewModel.onEvent(EventsScheduleScreenEvent.EventDateChanged(selectedDate))
            })
    }
}

fun getErrorMessage(error: ValidationError, context: Context) : String {
    return when(error) {
        is ValidationError.RequiredField -> {
            context.getString(R.string.err_details_required)
        }
        is ValidationError.EventScheduleError.PastTimeError -> {
            context.getString(R.string.err_event_past)
        }
        is ValidationError.EventScheduleError.TimeOverlappingError -> {
            context.getString(R.string.err_event_overlapping)
        }
        is ValidationError.EventScheduleError.EndTimeOlderError -> {
            context.getString(R.string.err_event_end_time)
        }
    }
}

