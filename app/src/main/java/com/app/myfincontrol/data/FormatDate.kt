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
        val calendar: Calendar = Calendar.getInstance(userTimeZone)
        when (sort) {
            ChartSort.MONTH -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
            }
            ChartSort.WEEK -> {
                calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.add(Calendar.DAY_OF_WEEK, 1) // Increment to the next day (Monday)
            }
            ChartSort.YEAR -> {
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
            }
            ChartSort.QUARTER -> {
                val currentMonth = calendar.get(Calendar.MONTH)
                val startMonthOfQuarter = currentMonth / 3 * 3
                calendar.set(Calendar.MONTH, startMonthOfQuarter)
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
            }
            else -> {
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
        val calendar: Calendar = Calendar.getInstance(userTimeZone)
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
                calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.add(Calendar.WEEK_OF_YEAR, 1) // Increment to the next week
                calendar.add(Calendar.SECOND, -1) // Go back one second to set to the end of the previous week
            }
            ChartSort.YEAR -> {
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.add(Calendar.YEAR, 1)
                calendar.add(Calendar.SECOND, -1)
            }
            ChartSort.QUARTER -> {
                val currentMonth = calendar.get(Calendar.MONTH)
                val startMonthOfNextQuarter = ((currentMonth / 3) + 1) * 3
                calendar.set(Calendar.MONTH, startMonthOfNextQuarter)
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
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