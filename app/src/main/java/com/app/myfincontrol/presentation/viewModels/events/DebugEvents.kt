package com.app.myfincontrol.presentation.viewModels.events

import com.app.myfincontrol.data.enums.TransactionType

sealed class DebugEvents {
    object ShowAlertDebugMode: DebugEvents()
    class GenerateTransactions(val type: TransactionType, val count: Int): DebugEvents()
}