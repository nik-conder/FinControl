package com.app.myfincontrol.presentation.viewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.app.myfincontrol.data.enums.ChartSort
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.domain.useCases.DataExchangeUseCase
import com.app.myfincontrol.domain.useCases.StatisticsUseCase
import com.app.myfincontrol.presentation.viewModels.events.StatisticsEvents
import com.app.myfincontrol.presentation.viewModels.states.StatisticsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticsUseCase: StatisticsUseCase,
    private val dataExchangeUseCase: DataExchangeUseCase
) : ViewModel() {

    private val _states = MutableStateFlow(StatisticsState())
    val states = _states.asStateFlow()

    init {
        getChart(type = TransactionType.INCOME, sort = states.value.chartCurrentSortIncome)
        getChart(type = TransactionType.EXPENSE, sort = states.value.chartCurrentSortExpense)
    }

    fun onEvents(event: StatisticsEvents) {
        when (event) {

            is StatisticsEvents.GetChart -> {
                getChart(type = event.type, sort = event.sort)
            }

            is StatisticsEvents.ExportToXlsx -> {
                exportChart(sort = event.sort, fileName = event.fileName)
            }

            is StatisticsEvents.DataExchangeAlert -> {
                dataExchangeAlert()
            }
        }
    }

    private fun dataExchangeAlert() {
        _states.update {
            it.copy(
                dataExchangeAlert = !states.value.dataExchangeAlert
            )
        }
    }

    private fun exportChart(sort: ChartSort, fileName: String) {
        val data = states.value.chartIncome // todo
        CoroutineScope(Dispatchers.IO).launch {
            val result = async {
                dataExchangeUseCase.exportToCsv(
                    sort = sort,
                    fileName = fileName,
                    data = data
                )
            }
            result.await()
        }
    }

    private fun getChart(type: TransactionType, sort: ChartSort) {
        _states.update {
            when (type) {
                TransactionType.INCOME -> {
                    it.copy(
                        chartIncome = statisticsUseCase.getChart(
                            type = TransactionType.INCOME,
                            sort = sort
                        ),
                        chartCurrentSortIncome = sort
                    )
                }

                TransactionType.EXPENSE -> {
                    it.copy(
                        chartExpense = statisticsUseCase.getChart(
                            type = TransactionType.EXPENSE,
                            sort = sort
                        ),
                        chartCurrentSortExpense = sort
                    )
                }
            }
        }
    }
}