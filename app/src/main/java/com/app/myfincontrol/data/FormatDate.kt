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
        when (sort) {
            ChartSort.MONTH -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            ChartSort.WEEK -> {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            ChartSort.YEAR -> {
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            ChartSort.QUARTER -> {
                val currentMonth = calendar.get(Calendar.MONTH)
                val startMonthOfQuarter = currentMonth / 3 * 3
                calendar.set(Calendar.MONTH, startMonthOfQuarter)
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            else -> {

            }
        }
        calendar.set(Calendar.ZONE_OFFSET,0)

        println("Date current: ${calendar.timeInMillis / 1000L}")
        return calendar.timeInMillis / 1000L
    }

    override fun getStartOfNextPeriod(sort: ChartSort): Long {
        val calendar: Calendar = Calendar.getInstance()
        when (sort) {
            ChartSort.MONTH -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.add(Calendar.MONTH, 1)
            }
            ChartSort.WEEK -> {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            ChartSort.YEAR -> {
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.add(Calendar.YEAR, 1)
                calendar.add(Calendar.DAY_OF_YEAR, -1)
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
            }
            else -> {

            }
        }
        calendar.set(Calendar.ZONE_OFFSET, 0)
        println("Date next: ${calendar.timeInMillis / 1000L}")
        return calendar.timeInMillis / 1000L
    }
}

/*

object FormatDateImpl : FormatDate {
    override fun getStartOfCurrentPeriod(sort: ChartSort): Long {
        val calendar: Calendar = Calendar.getInstance()
        when (sort) {
            ChartSort.MONTH -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            ChartSort.WEEK -> {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            ChartSort.YEAR -> {
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            ChartSort.QUARTER -> {
                val currentMonth = calendar.get(Calendar.MONTH)
                val startMonthOfQuarter = currentMonth / 3 * 3
                calendar.set(Calendar.MONTH, startMonthOfQuarter)
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }

            else -> {

            }
        }

        println("Date current: ${calendar.timeInMillis / 1000L}")
        return calendar.timeInMillis / 1000L
    }

    override fun getStartOfNextPeriod(sort: ChartSort): Long {
        val calendar: Calendar = Calendar.getInstance()
        when (sort) {
            ChartSort.MONTH -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.add(Calendar.MONTH, 1)
            }
            ChartSort.WEEK -> {
                // Установить календарь на последний день (воскресенье) текущей недели
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                // Добавить 6 дней для получения последнего дня недели
                calendar.add(Calendar.DAY_OF_WEEK, 6)
            }
            ChartSort.YEAR -> {
                // Установить календарь на первый день следующего года
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.add(Calendar.YEAR, 1)
                calendar.add(Calendar.DAY_OF_YEAR, -1)
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
            }

            else -> {

            }
        }
        println("Date next: ${calendar.timeInMillis / 1000L}, sort: $sort")
        return calendar.timeInMillis / 1000L
    }
}
 */