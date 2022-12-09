package com.taskwork.eventscheduler.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Long.toTimeMeridiem() : String {
    val formatter = SimpleDateFormat("hh:mm a")
    val date = Date(this)
    return formatter.format(date)
}

@SuppressLint("SimpleDateFormat")
fun Long.toTime() : String {
    val formatter = SimpleDateFormat("hh:mm")
    val date = Date(this)
    return formatter.format(date)
}

@SuppressLint("SimpleDateFormat")
fun Long.toDate() : String {
    val formatter = SimpleDateFormat("dd-MMM-yyyy")
    val date = Date(this)
    return formatter.format(date)
}