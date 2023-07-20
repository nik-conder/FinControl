package com.app.myfincontrol.domain.useCases

import com.app.myfincontrol.data.FormatDate
import com.app.myfincontrol.data.FormatDateImpl
import com.app.myfincontrol.data.enums.ChartSort
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.data.repositories.TransactionRepository
import com.patrykandpatrick.vico.core.entry.FloatEntry
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class StatisticsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    fun getChart(
        type: TransactionType,
        sort: ChartSort
    ): List<FloatEntry> {
        val startTime: Long = FormatDateImpl.getStartOfCurrentPeriod(sort)
        val endTime = FormatDateImpl.getStartOfNextPeriod(sort)

        val dataTransactions = transactionRepository.getChartTransactions(
            type = type,
            startTime = startTime,
            endTime = endTime
        )
        val list = mutableListOf<FloatEntry>()

        val transactionsByDay = dataTransactions.filter { transaction ->
            val date = Date(transaction.datetime * 1000)
            val calendar = Calendar.getInstance()
            calendar.time = date

            when (sort) {
                ChartSort.YEAR -> {
                    val currentYear = calendar.get(Calendar.YEAR)
                    calendar.get(Calendar.YEAR) == currentYear
                }
                ChartSort.QUARTER -> {
                    val currentQuarter = calendar.get(Calendar.MONTH) / 3
                    val startMonthOfQuarter = currentQuarter * 3
                    val endMonthOfQuarter = startMonthOfQuarter + 2
                    calendar.get(Calendar.MONTH) in startMonthOfQuarter..endMonthOfQuarter
                }
                ChartSort.MONTH -> {
                    val currentMonth = calendar.get(Calendar.MONTH)
                    val currentYear = calendar.get(Calendar.YEAR)
                    calendar.get(Calendar.MONTH) == currentMonth && calendar.get(Calendar.YEAR) == currentYear
                }
                ChartSort.DAY -> {
                    val currentDate = calendar.get(Calendar.DAY_OF_MONTH)
                    val currentMonth = calendar.get(Calendar.MONTH)
                    val currentYear = calendar.get(Calendar.YEAR)
                    calendar.get(Calendar.DAY_OF_MONTH) == currentDate && calendar.get(Calendar.MONTH) == currentMonth && calendar.get(Calendar.YEAR) == currentYear
                }
                else -> false
            }
        }.groupBy { transaction ->
            val date = Date(transaction.datetime * 1000)
            val calendar = Calendar.getInstance()
            calendar.time = date

            when (sort) {
                ChartSort.YEAR -> {
                    calendar.get(Calendar.YEAR)
                }
                ChartSort.QUARTER -> {
                    val currentQuarter = calendar.get(Calendar.MONTH) / 3
                    currentQuarter
                }
                ChartSort.MONTH -> {
                    val currentMonth = calendar.get(Calendar.MONTH)
                    currentMonth
                }
                ChartSort.DAY -> {
                    calendar.get(Calendar.DAY_OF_MONTH)
                }
                else -> 0
            }
        }.mapValues { entry ->
            entry.value.sumByDouble { transaction -> transaction.amount.toDouble() }
        }

        transactionsByDay.forEach {
            list.add(FloatEntry(x = it.key.toFloat(), y = it.value.toFloat()))
        }
        list.forEach {
            println("${it.x} ${it.y}")
        }
        return list
    }

}