package com.app.myfincontrol.domain.useCases

import android.os.Build
import androidx.annotation.RequiresApi
import com.app.myfincontrol.data.FormatDateImpl
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.enums.ChartSort
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.data.repositories.TransactionRepository
import com.patrykandpatrick.vico.core.entry.FloatEntry
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class StatisticsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {


    @RequiresApi(Build.VERSION_CODES.O)
    interface ChartSorter {
        fun sortData(data: List<Transaction>, startTime: Long, endTime: Long): List<FloatEntry>
    }

    @RequiresApi(Build.VERSION_CODES.O)
    class DaySorter : ChartSorter {
        val list = mutableListOf<FloatEntry>()
        @RequiresApi(Build.VERSION_CODES.O)
        override fun sortData(data: List<Transaction>, startTime: Long, endTime: Long): List<FloatEntry> {
            val transactionsByPeriod = data.filter { transaction ->
                val date = Date(transaction.datetime * 1000)
                val calendar = Calendar.getInstance()
                calendar.time = date

                val currentDate = LocalDate.ofEpochDay(transaction.datetime / (24 * 60 * 60))
                val currentDay = currentDate.dayOfMonth

                currentDay == calendar.get(Calendar.DAY_OF_MONTH)

            }.groupBy { transaction ->
                // Группируем данные по часам
                val date = Date(transaction.datetime * 1000)
                val calendar = Calendar.getInstance()
                calendar.time = date
                calendar.get(Calendar.HOUR_OF_DAY)
            }.mapValues { entry ->
                entry.value.sumOf { transaction -> transaction.amount.toDouble() }
            }
            transactionsByPeriod.forEach {
                list.add(FloatEntry(x = it.key.toFloat(), y = it.value.toFloat()))
            }

            return list
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    class WeekSorter : ChartSorter {
        val list = mutableListOf<FloatEntry>()
        @RequiresApi(Build.VERSION_CODES.O)
        override fun sortData(data: List<Transaction>, startTime: Long, endTime: Long): List<FloatEntry> {
            val transactionsByPeriod = data.filter { transaction ->
                val date = Date(transaction.datetime * 1000)
                val calendar = Calendar.getInstance()
                calendar.time = date

                val currentDate = LocalDate.ofEpochDay(transaction.datetime / (24 * 60 * 60))
                val currentWeek =
                    currentDate.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())

                // Фильтруем данные, чтобы в результате остались только те, которые относятся к текущей неделе
                currentWeek == calendar.get(Calendar.WEEK_OF_YEAR)

            }.groupBy { transaction ->
                // группируем данные по дням
                val date = Date(transaction.datetime * 1000)
                val calendar = Calendar.getInstance()
                calendar.time = date
                calendar.get(Calendar.DAY_OF_MONTH)
            }.mapValues { entry ->
                entry.value.sumOf { transaction -> transaction.amount.toDouble() }
            }

            transactionsByPeriod.forEach {
                list.add(FloatEntry(x = it.key.toFloat(), y = it.value.toFloat()))
            }

            return list
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    class MonthSorter : ChartSorter {
        val list = mutableListOf<FloatEntry>()
        override fun sortData(
            data: List<Transaction>,
            startTime: Long,
            endTime: Long
        ): List<FloatEntry> {
            val transactionsByPeriod = data.filter { transaction ->
                val date = Date(transaction.datetime * 1000)
                val calendar = Calendar.getInstance()
                calendar.time = date

                val currentDate = LocalDate.ofEpochDay(transaction.datetime / (24 * 60 * 60))
                val currentMonth = currentDate.monthValue

                 currentMonth == calendar.get(Calendar.WEEK_OF_MONTH)

            }.groupBy { transaction ->
                // Группируем данные по дням месяца
                val date = Date(transaction.datetime * 1000)
                val calendar = Calendar.getInstance()
                calendar.time = date
                calendar.get(Calendar.DAY_OF_MONTH)
            }.mapValues { entry ->
                entry.value.sumOf { transaction -> transaction.amount.toDouble() }
            }
            transactionsByPeriod.forEach {
                list.add(FloatEntry(x = it.key.toFloat(), y = it.value.toFloat()))
            }

            return list
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    class QuartetSorter : ChartSorter {
        override fun sortData(
            data: List<Transaction>,
            startTime: Long,
            endTime: Long
        ): List<FloatEntry> {
            return emptyList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    class YearSorter : ChartSorter {
        override fun sortData(
            data: List<Transaction>,
            startTime: Long,
            endTime: Long
        ): List<FloatEntry> {
            return emptyList()
        }
    }

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

        val list = when (sort) {
            ChartSort.DAY -> DaySorter()
            ChartSort.WEEK -> WeekSorter()
            ChartSort.MONTH -> MonthSorter()
            ChartSort.QUARTER -> QuartetSorter()
            ChartSort.YEAR -> YearSorter()
        }

        return list.sortData(dataTransactions, startTime, endTime)
    }
}