package com.app.myfincontrol.presentation.viewModels.states

import com.app.myfincontrol.data.enums.ChartSort
import com.patrykandpatrick.vico.core.entry.FloatEntry

data class StatisticsState(
    val isLoading: Boolean = false,
    val chartIncome: List<FloatEntry> = emptyList(),
    val chartExpense: List<FloatEntry> = emptyList(),
    val chartCurrentSortIncome: ChartSort = ChartSort.WEEK,
    val chartCurrentSortExpense: ChartSort = ChartSort.WEEK

)
