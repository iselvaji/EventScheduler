package com.taskwork.eventscheduler.util

import java.util.*
import java.util.concurrent.TimeUnit

object TimeUtil {

    fun isOverlapping(startDateTime: Long, endDateTime: Long,
                      startDateTimeToCompare: Long, endDateTimeToCompare: Long): Boolean {

        val startDate1 = Date(startDateTime)
        val endDate1 = Date(endDateTime)

        val startDate2 = Date(startDateTimeToCompare)
        val endDate2= Date(endDateTimeToCompare)

        return startDate1 <= endDate2 && endDate1 >= startDate2
    }

    fun durationBetween(startTime : Long, endTime : Long) : String {

        val diffInMilliSec = endTime - startTime
        val diffInHours: Long = TimeUnit.MILLISECONDS.toHours(diffInMilliSec) % 24
        val diffInMin: Long = TimeUnit.MILLISECONDS.toMinutes(diffInMilliSec) % 60

        val result = StringBuilder("")

        if(diffInHours > 0) {
            var hours = " Hours"
            if(diffInHours.toInt() == 1)
                hours = " Hour"
            result.append(diffInHours).append(hours)
        }
        if(diffInMin > 0) {
            result.append(diffInMin).append(" Mins ")
        }
        return result.toString()
    }

    fun isToday(date: Long): Boolean {
        return isSameDay(Date(date), Calendar.getInstance().time)
    }

    private fun isSameDay(date1: Date?, date2: Date?): Boolean {
        require(!(date1 == null || date2 == null)) { "The dates must not be null" }
        val cal1 = Calendar.getInstance()
        cal1.time = date1
        val cal2 = Calendar.getInstance()
        cal2.time = date2
        return isSameDay(cal1, cal2)
    }

    private fun isSameDay(cal1: Calendar?, cal2: Calendar?): Boolean {
        require(!(cal1 == null || cal2 == null)) { "The dates must not be null" }
        return cal1[Calendar.ERA] == cal2[Calendar.ERA]
                && cal1[Calendar.YEAR] == cal2[Calendar.YEAR]
                && cal1[Calendar.DAY_OF_YEAR] == cal2[Calendar.DAY_OF_YEAR]
    }

}