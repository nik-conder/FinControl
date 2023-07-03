package com.app.myfincontrol.presentation.viewModels.states

import com.patrykandpatrick.vico.core.entry.FloatEntry

data class StatisticsState(
    val isLoading: Boolean = false,
    val chartIncomeCurrentMonth: List<FloatEntry> = emptyList(),
    val chartExpenseCurrentMonth: List<FloatEntry> = emptyList(),
    val chartIncomeCurrentWeek: List<FloatEntry> = emptyList(),
    val chartExpenseCurrentWeek: List<FloatEntry> = emptyList(),

)
