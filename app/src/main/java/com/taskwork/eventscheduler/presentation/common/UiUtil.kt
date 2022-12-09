package com.taskwork.eventscheduler.presentation.common

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import java.util.*

object UiUtil {

    @SuppressLint("SimpleDateFormat")
    @Composable
    fun TimePicker(
        openDialog: MutableState<Boolean>,
        onTimePicked: (time: Long) -> Unit)
    {
        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]

        val timePickerDialog =
            TimePickerDialog(context,
                {_, hour : Int, minute: Int ->
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    onTimePicked.invoke(calendar.timeInMillis)
                }, hour, minute, false)

        openDialog.value = false
        timePickerDialog.show()
    }


    @SuppressLint("SimpleDateFormat")
    @Composable
    fun DatePicker(
        openDialog: MutableState<Boolean>,
        onDatePicked: (date: Long) -> Unit)
    {
        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        calendar.time = Date()

        val datePickerDialog = DatePickerDialog(context,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                calendar.set(mYear, mMonth, mDayOfMonth)
                onDatePicked.invoke(calendar.time.time)
            }, year, month, day
        )

        openDialog.value = false
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }
}