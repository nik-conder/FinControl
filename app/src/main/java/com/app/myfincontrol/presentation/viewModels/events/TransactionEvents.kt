package com.app.myfincontrol.presentation.viewModels.events

import com.app.myfincontrol.data.entities.Transactions

sealed class TransactionEvents {
    class AddTransaction(val transactions: Transactions) : TransactionEvents()

    object GenerateEvents : TransactionEvents()
}