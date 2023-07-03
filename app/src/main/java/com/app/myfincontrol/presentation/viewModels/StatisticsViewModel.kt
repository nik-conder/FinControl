package com.app.myfincontrol.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.app.myfincontrol.data.enums.ChartSort
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.domain.useCases.StatisticsUseCase
import com.app.myfincontrol.presentation.viewModels.events.StatisticsEvents
import com.app.myfincontrol.presentation.viewModels.states.LoginState
import com.app.myfincontrol.presentation.viewModels.states.StatisticsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticsUseCase: StatisticsUseCase
): ViewModel() {

    private val _states = MutableStateFlow(StatisticsState())
    val states = _states.asStateFlow()

    init {
        onEvents(StatisticsEvents.GetChart(type = TransactionType.INCOME, sort = ChartSort.MONTH))
    }

    fun onEvents(event: StatisticsEvents) {
        when (event) {
            is StatisticsEvents.GetChart -> {
                if (event.type == TransactionType.INCOME) {
                    if (event.sort == ChartSort.MONTH) {
                        _states.value = _states.value.copy(chartIncomeCurrentMonth = statisticsUseCase.getChartIncomeMonth())
                    } else if (event.sort == ChartSort.WEEK) {
                        _states.value = _states.value.copy(chartIncomeCurrentWeek = statisticsUseCase.getChartIncomeWeek())
                    }
                } else {
                    _states.value = _states.value.copy(chartExpenseCurrentMonth = statisticsUseCase.getChartExpenseMonth())
                }

            }
        }
    }

}