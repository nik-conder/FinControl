package com.app.myfincontrol.presentation.viewModels.events

import com.app.myfincontrol.data.entities.Transaction


sealed class TransactionEvents {
    class AddTransaction(val transactions: Transaction) : TransactionEvents()

    object GenerateEvents : TransactionEvents()

    object LoadTransactions: TransactionEvents()
}