package com.app.myfincontrol.presentation.viewModels.events

import com.app.myfincontrol.data.enums.ChartSort
import com.app.myfincontrol.data.enums.TransactionType

sealed class StatisticsEvents {

    class GetChart(val type: TransactionType, val sort: ChartSort) : StatisticsEvents()
    object ExportToXlsx : StatisticsEvents()
    object DataExchangeAlert : StatisticsEvents()
}