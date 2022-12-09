package com.taskwork.eventscheduler.presentation.eventslist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import com.taskwork.eventscheduler.R
import com.taskwork.eventscheduler.domain.model.EventInfo
import com.taskwork.eventscheduler.util.Constants
import com.taskwork.eventscheduler.util.Constants.TEST_TAG_ADD_EVENT
import com.taskwork.eventscheduler.util.Constants.TEST_TAG_DURATION
import com.taskwork.eventscheduler.util.Constants.TEST_TAG_EVENTS_LISTVIEW
import com.taskwork.eventscheduler.util.Constants.TEST_TAG_NAME
import com.taskwork.eventscheduler.util.TimeUtil.durationBetween
import com.taskwork.eventscheduler.util.rememberLifecycleEvent
import com.taskwork.eventscheduler.util.toDate
import com.taskwork.eventscheduler.util.toTimeMeridiem

@Composable
fun EventsListScreen(viewModel : EventsListViewModel,
                     onNextClick: () -> Unit) {

    val lifecycleEvent = rememberLifecycleEvent()
    val state = viewModel.stateEvents

    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.onEvent(EventsListScreenEvent.FetchEvents)
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNextClick()
                },
                content = {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = stringResource(R.string.add),
                        Modifier.testTag(TEST_TAG_ADD_EVENT)
                    )
                }
            )
        },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            DisplayEventsList(data = state.collectAsState().value)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun DisplayEventsList(data: List<EventInfo>) {

    if(data.isEmpty()) {
        EventsEmptyUI()
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().testTag(TEST_TAG_EVENTS_LISTVIEW)
    ) {
        val grouped = data.groupBy{it.date.toDate()}
        grouped.forEach { (eventDate, events) ->
            stickyHeader {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    text = eventDate
                )
            }
            items(events.size) { i ->
                ListEventItem(
                    data = events[i],
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListEventItem(data : EventInfo, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp),
        shape = RoundedCornerShape(size = 8.dp),
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = data.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                .testTag(TEST_TAG_NAME)
            )

            Text(
                text = data.startTime.toTimeMeridiem() + " - " + data.endTime.toTimeMeridiem(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                //.testTag(TEST_TAG_NAME)
            )

            Text(
                text = durationBetween(data.startTime, data.endTime),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                .testTag(TEST_TAG_DURATION)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventsEmptyUI(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.events_empty),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.testTag(Constants.TEST_TAG_NO_EVENT)
        )
    }
}
