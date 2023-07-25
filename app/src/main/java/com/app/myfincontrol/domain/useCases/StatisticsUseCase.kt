package com.app.myfincontrol.domain.useCases

import android.os.Build
import androidx.annotation.RequiresApi
import com.app.myfincontrol.data.FormatDateImpl
import com.app.myfincontrol.data.enums.ChartSort
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.data.repositories.TransactionRepository
import com.patrykandpatrick.vico.core.entry.FloatEntry
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class StatisticsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getChart(
        type: TransactionType,
        sort: ChartSort
    ): List<FloatEntry> {

        val startTime: Long = FormatDateImpl.getStartPeriod(sort)
        val endTime = FormatDateImpl.getEndPeriod(sort)
        val dataTransactions = transactionRepository.getChartTransactions(
            type = type,
            startTime = startTime,
            endTime = endTime
        )
        val list = mutableListOf<FloatEntry>()

        val transactionsByPeriod = dataTransactions.filter { transaction ->
            val date = Date(transaction.datetime * 1000)
            val test = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() // todo
            val calendar = Calendar.getInstance()
            calendar.time = date
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentYear = calendar.get(Calendar.YEAR)
            val currentDay = calendar.get(Calendar.DAY_OF_WEEK)
            val currentWeek = calendar.get(Calendar.WEEK_OF_MONTH)

            calendar.get(Calendar.MONTH) == currentMonth
                    && calendar.get(Calendar.YEAR) == currentYear
                    && calendar.get(Calendar.DAY_OF_WEEK) == currentDay
                    && calendar.get(Calendar.WEEK_OF_MONTH) == currentWeek

        }.groupBy { transaction ->
            val date = Date(transaction.datetime * 1000)

            println(transaction)
            when (sort) {
                ChartSort.MONTH -> {
                    getCurrentMonth(transaction.datetime)
                }

                ChartSort.WEEK -> {
                    getCurrentWeek(transaction.datetime)
                }

                ChartSort.YEAR -> {
                    getCurrentYear(transaction.datetime)
                }

                ChartSort.QUARTER -> {
                    getCurrentQuarter(transaction.datetime)
                }

                else -> {
                    getCurrentDay(transaction.datetime)
                }

            }

        }.mapValues { entry ->
            entry.value.sumOf { transaction -> transaction.amount.toDouble() }
        }
        transactionsByPeriod.forEach {
            list.add(FloatEntry(x = it.key.toFloat(), y = it.value.toFloat()))
        }

        println("transactionsByPeriod:")
        println(transactionsByPeriod)

        list.forEach {
            println("${it.x} ${it.y}")
        }

        println(list)
        return list
    }

    // Вспомогательная функция для получения часа из Unix-времени
    fun getCurrentDay(timeUnix: Long): Int {
        val date = Date(timeUnix * 1000)
        return SimpleDateFormat("HH").format(date).toInt()
    }

    private fun getCurrentYear(timeUnix: Long): Int {
        val date = Date(timeUnix * 1000)
        val calendar = Calendar.getInstance().apply {
            time = date
        }
        return calendar.get(Calendar.YEAR)
    }

    private fun getCurrentQuarter(timeUnix: Long): Int {
        val date = Date(timeUnix * 1000)
        val calendar = Calendar.getInstance().apply {
            time = date
        }
        val currentMonth = calendar.get(Calendar.MONTH) // Month value is zero-based (0 to 11)

        // Calculate the quarter based on the current month
        return (currentMonth / 3) + 1
    }


    private fun getCurrentMonth(timeUnix: Long): Int {
        val date = Date(timeUnix * 1000)
        val calendar = Calendar.getInstance().apply {
            time = date
        }
        return calendar.get(Calendar.MONTH) + 1 // Adding 1 to get month number from 1 to 12
    }


    private fun getCurrentWeek(timeUnix: Long): Int {
        val date = Date(timeUnix * 1000)
        val calendar = Calendar.getInstance().apply {
            time = date
        }
        var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY + 1
        if (dayOfWeek <= 0) {
            dayOfWeek += 7 // Коррекция для воскресенья (Calendar.SUNDAY = 1)
        }
        return dayOfWeek
    }



}