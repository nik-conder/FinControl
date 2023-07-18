package com.app.myfincontrol.presentation.viewModels.states

import com.patrykandpatrick.vico.core.entry.FloatEntry

data class StatisticsState(
    val isLoading: Boolean = false,
    val chartIncome: List<FloatEntry> = emptyList(),
    val chartExpense: List<FloatEntry> = emptyList(),

)
