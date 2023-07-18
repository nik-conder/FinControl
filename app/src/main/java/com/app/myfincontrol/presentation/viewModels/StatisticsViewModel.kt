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
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticsUseCase: StatisticsUseCase
): ViewModel() {

    private val _states = MutableStateFlow(StatisticsState())
    val states = _states.asStateFlow()

    init {
        onEvents(StatisticsEvents.GetChart(type = TransactionType.INCOME, sort = ChartSort.MONTH))
        onEvents(StatisticsEvents.GetChart(type = TransactionType.EXPENSE, sort = ChartSort.MONTH))
    }

    fun onEvents(event: StatisticsEvents) {
        when (event) {
            is StatisticsEvents.GetChart -> {
                _states.update {
                    if (event.type == TransactionType.INCOME) {
                        it.copy(
                            chartIncome = statisticsUseCase.getChart(
                                type = event.type,
                                sort = event.sort
                            )
                        )
                    } else {
                        it.copy(
                            chartExpense = statisticsUseCase.getChart(
                                type = event.type,
                                sort = event.sort
                            )
                        )
                    }

                }
            }
        }
    }

}