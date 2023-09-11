package com.app.myfincontrol.presentation.viewModels.events

import com.app.myfincontrol.data.enums.ChartSort
import com.app.myfincontrol.data.enums.TransactionType
import com.patrykandpatrick.vico.core.entry.FloatEntry

sealed class StatisticsEvents {

    class GetChart(val type: TransactionType, val sort: ChartSort) : StatisticsEvents()
    class ExportToXlsx(val sort: ChartSort, val fileName: String) :
        StatisticsEvents()

    object DataExchangeAlert : StatisticsEvents()
}