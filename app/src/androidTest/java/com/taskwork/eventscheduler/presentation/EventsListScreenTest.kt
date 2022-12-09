package com.taskwork.eventscheduler.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.common.truth.Truth.assertThat
import com.taskwork.eventscheduler.MainActivity
import com.taskwork.eventscheduler.R
import com.taskwork.eventscheduler.TestUtil.waitUntilDoesNotExist
import com.taskwork.eventscheduler.presentation.eventschedule.EventScheduleScreen
import com.taskwork.eventscheduler.presentation.eventslist.EventsListScreen
import com.taskwork.eventscheduler.presentation.navigation.Route
import com.taskwork.eventscheduler.ui.theme.EventSchedulerTheme
import com.taskwork.eventscheduler.util.Constants.TEST_TAG_ADD_EVENT
import com.taskwork.eventscheduler.util.Constants.TEST_TAG_EVENTS_LISTVIEW
import com.taskwork.eventscheduler.util.Constants.TEST_TAG_NAME
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.compose.getViewModel
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
@LargeTest
class EventsListScreenTest : KoinTest{

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        composeRule.setContent {
            navController = rememberNavController()
            
            EventSchedulerTheme {
                NavHost(navController = navController,
                        startDestination = Route.EVENT_LIST) {
                    composable(Route.EVENT_LIST) {
                        EventsListScreen(
                            getViewModel(),
                            onNextClick = {
                                navController.navigate(Route.EVENT_SCHEDULE)
                            }
                        )
                    }
                    composable(Route.EVENT_SCHEDULE) {
                        EventScheduleScreen(
                            getViewModel(),
                            onNextClick = {
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }

    @Test
    fun test_isNoEventsTextVisible() {
        val emptyDataMsg = composeRule.activity.getString(R.string.events_empty)
        composeRule.onNodeWithText(emptyDataMsg).assertIsDisplayed()
    }

    @Test
    fun test_EventsListVisible() {
        composeRule.onNodeWithTag(TEST_TAG_NAME).assertIsDisplayed()
        composeRule.onNodeWithTag(TEST_TAG_EVENTS_LISTVIEW).onChildren().assertCountEquals(1)
    }

    @Test
    fun test_EventsCreateScreenNavigation() {
        val addDescription = composeRule.activity.getString(R.string.add)
        composeRule.onNodeWithContentDescription(addDescription).apply {
            assertIsDisplayed()
            performClick()
        }
        composeRule.waitUntilDoesNotExist(hasTestTag(TEST_TAG_ADD_EVENT))
        Thread.sleep(100)
        val route = navController.currentBackStackEntry?.destination?.route
        assertThat(route).isEqualTo(Route.EVENT_SCHEDULE)
    }
}
