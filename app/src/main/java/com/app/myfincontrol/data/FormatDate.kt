package com.app.myfincontrol.data

import com.app.myfincontrol.data.enums.ChartSort
import java.util.Calendar

interface FormatDate {
    fun getStartOfCurrentPeriod(sort: ChartSort): Long
    fun getStartOfNextPeriod(sort: ChartSort): Long
}

object FormatDateImpl : FormatDate {
    override fun getStartOfCurrentPeriod(sort: ChartSort): Long {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        println("Date current: ${calendar.timeInMillis / 1000L}")
        return calendar.timeInMillis / 1000L
    }

    override fun getStartOfNextPeriod(sort: ChartSort): Long {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.add(Calendar.MONTH, 1)
        println("Date next: ${calendar.timeInMillis / 1000L}")
        return calendar.timeInMillis / 1000L
    }
}