package com.app.myfincontrol.data

import com.app.myfincontrol.data.enums.ChartSort
import java.util.Calendar
import java.util.TimeZone

interface FormatDate {
    val userTimeZone: TimeZone
        get() = TimeZone.getDefault()

    fun getStartPeriod(sort: ChartSort): Long
    fun getEndPeriod(sort: ChartSort): Long
}

object FormatDateImpl : FormatDate {
    override fun getStartPeriod(sort: ChartSort): Long {
        val calendar: Calendar = Calendar.getInstance(userTimeZone) // Pass the default time zone of the device
        when (sort) {
            ChartSort.MONTH -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1) // Set day to the 1st day of the current month
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
            }
            ChartSort.WEEK -> {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY) // Set day to the first day (Monday) of the current week
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
            }
            ChartSort.YEAR -> {
                calendar.set(Calendar.DAY_OF_YEAR, 1) // Set day to the 1st day of the current year
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
            }
            ChartSort.QUARTER -> {
                val currentMonth = calendar.get(Calendar.MONTH)
                val startMonthOfQuarter = currentMonth / 3 * 3
                calendar.set(Calendar.MONTH, startMonthOfQuarter) // Set month to the first month of the current quarter
                calendar.set(Calendar.DAY_OF_MONTH, 1) // Set day to the 1st day of the first month of the current quarter
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
            }
            else -> { // For DAY and other unrecognized cases
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
        }

        println("Date current: ${calendar.timeInMillis / 1000L}")
        return calendar.timeInMillis / 1000L
    }

    override fun getEndPeriod(sort: ChartSort): Long {
        val calendar: Calendar = Calendar.getInstance(userTimeZone)// Pass the default time zone of the device
        when (sort) {
            ChartSort.MONTH -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.add(Calendar.MONTH, 1)
                calendar.add(Calendar.SECOND, -1)
            }
            ChartSort.WEEK -> {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY) // Set day to the last day (Sunday) of the current week
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                calendar.set(Calendar.MILLISECOND, 999)
            }
            ChartSort.YEAR -> {
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.add(Calendar.YEAR, 1)
                calendar.add(Calendar.DAY_OF_YEAR, -1)
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                calendar.set(Calendar.MILLISECOND, 999)
            }
            ChartSort.QUARTER -> {
                val currentMonth = calendar.get(Calendar.MONTH)
                val startMonthOfNextQuarter = ((currentMonth / 3) + 1) * 3
                calendar.set(Calendar.MONTH, startMonthOfNextQuarter)
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.add(Calendar.SECOND, -1)
            }
            else -> {
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                calendar.set(Calendar.MILLISECOND, 999)
            }
        }
        println("Date next: ${calendar.timeInMillis / 1000L}")
        return calendar.timeInMillis / 1000L
    }
}
