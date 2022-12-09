package com.taskwork.eventscheduler

import android.R
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import java.util.*

object TestUtil {

    fun ComposeContentTestRule.waitUntilDoesNotExist(
        matcher: SemanticsMatcher,
        timeoutMillis: Long = 10_000L
    ) {
        return this.waitUntilNodeCount(matcher, 0, timeoutMillis)
    }

    private fun ComposeContentTestRule.waitUntilNodeCount(
        matcher: SemanticsMatcher,
        count: Int,
        timeoutMillis: Long = 10_000L
    ) {
        this.waitUntil(timeoutMillis) {
            this.onAllNodes(matcher).fetchSemanticsNodes().size == count
        }
    }

    fun setTimeInPicker(minutes : Int){
        val calendar = Calendar.getInstance()
        onView(isAssignableFrom(TimePicker::class.java)).perform(
            PickerActions.setTime(
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE) + minutes
            )
        )
        onView(withId(R.id.button1)).perform(click())
    }

    fun setDateInPicker(day : Int){
        val calendar = Calendar.getInstance()
        onView(isAssignableFrom(DatePicker::class.java)).perform(
            PickerActions.setDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH) + day)
        )
        onView(withId(R.id.button1)).perform(click())
    }

}