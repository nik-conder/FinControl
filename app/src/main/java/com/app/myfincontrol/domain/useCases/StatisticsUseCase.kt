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
        val startTime: Long = FormatDateImpl.getStartOfCurrentPeriod(ChartSort.MONTH)
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
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentYear = calendar.get(Calendar.YEAR)
            calendar.get(Calendar.MONTH) == currentMonth && calendar.get(Calendar.YEAR) == currentYear
        }.groupBy { transaction ->
            val date = Date(transaction.datetime * 1000)
            SimpleDateFormat("dd").format(date)
        }.mapValues { entry ->
            entry.value.sumByDouble { transaction -> transaction.amount.toDouble() }
        }

        transactionsByDay.forEach {
            list.add(FloatEntry(x = it.key.toFloat(), y = it.value.toFloat()))
        }
        return list
    }
}