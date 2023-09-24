package com.app.myfincontrol.domain.useCases

import android.os.Build
import androidx.annotation.RequiresApi
import com.app.myfincontrol.data.FormatDateImpl
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.enums.ChartSort
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.data.repositories.TransactionRepository
import com.patrykandpatrick.vico.core.entry.FloatEntry
import java.util.Calendar
import java.util.Date
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

        val result = sortData(dataTransactions, startTime, endTime, sort)

        println("==== входные данные ====")
        println(dataTransactions)
        println("==== входные данные (конец) ====")

        println("=== выходные данные ===")
        println(result)
        println("=== выходные данные (конец) ===")

        return result
    }

    private fun sortData(data: List<Transaction>, startTime: Long, endTime: Long, sort: ChartSort): List<FloatEntry> {

        val list = mutableListOf<FloatEntry>()

        val transactionsByPeriod = data.filter { transaction ->
            transaction.datetime in (startTime + 1) until endTime
        }.groupBy { transaction ->
            val date = Date(transaction.datetime * 1000)
            val calendar = Calendar.getInstance()
            calendar.time = date
            when (sort) {
                ChartSort.DAY -> calendar.get(Calendar.HOUR_OF_DAY)
                ChartSort.WEEK -> calendar.get(Calendar.DAY_OF_MONTH)
                ChartSort.MONTH -> calendar.get(Calendar.DAY_OF_MONTH)
                ChartSort.YEAR -> calendar.get(Calendar.MONTH) + 1
                ChartSort.QUARTER -> calendar.get(Calendar.MONTH) + 1
            }
        }.mapValues { entry ->
            entry.value.sumOf { transaction -> transaction.amount.toDouble() }
        }
        transactionsByPeriod.forEach {
            list.add(FloatEntry(x = it.key.toFloat(), y = it.value.toFloat()))
        }
        println("transactionsByPeriod $transactionsByPeriod")
        return list
    }
}