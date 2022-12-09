package com.taskwork.eventscheduler.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.taskwork.eventscheduler.MainActivity
import com.taskwork.eventscheduler.R
import com.taskwork.eventscheduler.TestUtil
import com.taskwork.eventscheduler.presentation.eventschedule.EventScheduleScreen
import com.taskwork.eventscheduler.presentation.eventslist.EventsListScreen
import com.taskwork.eventscheduler.presentation.navigation.Route
import com.taskwork.eventscheduler.ui.theme.EventSchedulerTheme
import com.taskwork.eventscheduler.util.Constants
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.compose.getViewModel
import org.koin.test.KoinTest
import kotlin.random.Random


@RunWith(AndroidJUnit4::class)
@LargeTest
class EventsScheduleScreenTest : KoinTest{

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

        val addDescription = composeRule.activity.getString(R.string.add)
        composeRule.onNodeWithContentDescription(addDescription).apply {
            performClick()
        }
    }

    @Test
    fun test_requiredFieldValidationError() {
        composeRule.apply {
            onNodeWithTag(Constants.TEST_TAG_EVENT_CREATE).apply {
                assertIsDisplayed()
                performClick()
            }
            val errDetailsRequired = activity.getString(R.string.err_details_required)
            onAllNodesWithText(errDetailsRequired).assertCountEquals(4)
        }
    }

    @Test
    fun test_eventEndTimeValidationError() {

        composeRule.apply {
            onNodeWithText(composeRule.activity.getString(R.string.startTime)).performClick()
            onView(withId(android.R.id.button1)).perform(click())

            onNodeWithText(composeRule.activity.getString(R.string.endTime)).performClick()
            onView(withId(android.R.id.button1)).perform(click())

            onNodeWithTag(Constants.TEST_TAG_EVENT_CREATE).apply {
                assertIsDisplayed()
                performClick()
            }

            val errEndTimeMsg = activity.getString(R.string.err_event_end_time)
            onNodeWithText(errEndTimeMsg).assertIsDisplayed()
        }
    }

    @Test
    fun test_eventCantBeInPastError() {

        composeRule.apply {

            onNodeWithText(composeRule.activity.getString(R.string.date)).performClick()
            onView(withId(android.R.id.button1)).perform(click())

            onNodeWithText(composeRule.activity.getString(R.string.startTime)).performClick()
            TestUtil.setTimeInPicker(-60)

            onNodeWithText(composeRule.activity.getString(R.string.endTime)).performClick()
            onView(withId(android.R.id.button1)).perform(click())

            onNodeWithTag(Constants.TEST_TAG_EVENT_CREATE).apply {
                assertIsDisplayed()
                performClick()
            }

            val errEndTimeMsg = activity.getString(R.string.err_event_past)
            onNodeWithText(errEndTimeMsg).assertIsDisplayed()
        }
    }

    @Test
    fun test_eventOverlapError() {

        composeRule.apply {

            onNodeWithText(activity.getString(R.string.name)).performTextInput("Test")

            onNodeWithText(activity.getString(R.string.date)).performClick()
            onView(withId(android.R.id.button1)).perform(click())

            onNodeWithText(activity.getString(R.string.startTime)).performClick()
            TestUtil.setTimeInPicker(0)

            onNodeWithText(activity.getString(R.string.endTime)).performClick()
            TestUtil.setTimeInPicker(30)

            onNodeWithTag(Constants.TEST_TAG_EVENT_CREATE).apply {
                assertIsDisplayed()
                performClick()
            }

            val overlapErrMsg = activity.getString(R.string.err_event_overlapping)
            onNodeWithText(overlapErrMsg).assertIsDisplayed()
        }
    }

    @Test
    fun test_eventCreatedSuccessfully() {

        composeRule.apply {

            onNodeWithText(activity.getString(R.string.name)).performTextInput("TestEvent"+ Random.nextInt())

            onNodeWithText(activity.getString(R.string.date)).performClick()
            TestUtil.setDateInPicker(2)

            onNodeWithText(activity.getString(R.string.startTime)).performClick()
            TestUtil.setTimeInPicker(0)

            onNodeWithText(activity.getString(R.string.endTime)).performClick()
            TestUtil.setTimeInPicker(30)

            onNodeWithTag(Constants.TEST_TAG_EVENT_CREATE).apply {
                assertIsDisplayed()
                performClick()
            }

            val overlapErrMsg = activity.getString(R.string.err_event_overlapping)
            onNodeWithText(overlapErrMsg).assertDoesNotExist()
        }
    }
}
