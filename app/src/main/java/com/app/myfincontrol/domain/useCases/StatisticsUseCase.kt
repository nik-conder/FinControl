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
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
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
            val calendar = Calendar.getInstance()
            calendar.time = date
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentYear = calendar.get(Calendar.YEAR)
            val currentDay = calendar.get(Calendar.DAY_OF_WEEK)
            calendar.get(Calendar.MONTH) == currentMonth && calendar.get(Calendar.YEAR) == currentYear && calendar.get(Calendar.DAY_OF_WEEK) == currentDay
        }.groupBy { transaction ->
            when (sort) {
                ChartSort.MONTH -> {
                    getCurrentMonth()
                }
                ChartSort.WEEK -> {
                    getCurrentWeek()
                }
                ChartSort.YEAR -> {
                    getCurrentYear()
                }
                ChartSort.QUARTER -> {
                    getCurrentQuarter()
                }
                else -> {
                    getCurrentHour(transaction.datetime)
                }
            }
        }.mapValues { entry ->
            entry.value.sumByDouble { transaction -> transaction.amount.toDouble() }
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
    fun getCurrentHour(timeUnix: Long): Int {
        val date = Date(timeUnix * 1000)
        return SimpleDateFormat("HH").format(date).toInt()
    }
    fun getCurrentYear(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR)
    }

    fun getCurrentQuarter(): Int {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        return (currentMonth / 3) + 1
    }

    fun getCurrentMonth(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH) + 1
    }

    fun getCurrentWeek(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.WEEK_OF_YEAR)
    }



}