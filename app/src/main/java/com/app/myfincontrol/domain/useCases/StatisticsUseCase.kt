package com.app.myfincontrol.domain.useCases

import com.app.myfincontrol.data.repositories.TransactionRepository
import com.patrykandpatrick.vico.core.entry.FloatEntry
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class StatisticsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    class FormatDate() {
        object Month {
            fun getStartOfCurrentMonth(): Long {
                val calendar: Calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                println("Date current: ${calendar.timeInMillis / 1000L}")
                return calendar.timeInMillis / 1000L
            }

            fun getStartOfNextMonth(): Long {
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
        object Week {
            fun getStartOfCurrentWeek(): Long {
                val calendar: Calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                println("Date current: ${calendar.timeInMillis / 1000L}")
                return calendar.timeInMillis / 1000L
            }
            fun getStartOfNextWeek(): Long {
                val calendar: Calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.add(Calendar.WEEK_OF_MONTH, 1)
                println("Date next: ${calendar.timeInMillis / 1000L}")
                return calendar.timeInMillis / 1000L
            }
        }
    }

    fun getChartIncomeMonth(): List<FloatEntry> {
        val startCurrentMonth = FormatDate.Month.getStartOfCurrentMonth()
        val startOfNextMonth = FormatDate.Month.getStartOfNextMonth()
        val transactions = transactionRepository.getIncomesForCurrentMonth(startOfMonth = startCurrentMonth, startOfNextMonth = startOfNextMonth)
        val list = mutableListOf<FloatEntry>()

        val transactionsByDay = transactions.filter { transaction ->
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

    fun getChartIncomeWeek(): List<FloatEntry> {
        val startCurrentWeek = FormatDate.Week.getStartOfCurrentWeek()
        val startOfNextWeek = FormatDate.Week.getStartOfNextWeek()
        val transactions = transactionRepository.getIncomesForCurrentWeek(startOfWeek = startCurrentWeek, startOfNextWeek = startOfNextWeek)
        val list = mutableListOf<FloatEntry>()

        val transactionsByDay = transactions.filter { transaction ->
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

    fun getChartExpenseMonth(): List<FloatEntry> {
        TODO("Not yet implemented")
    }
}