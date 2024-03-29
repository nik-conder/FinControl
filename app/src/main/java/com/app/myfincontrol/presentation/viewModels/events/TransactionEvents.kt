package com.app.myfincontrol.presentation.viewModels.events

import com.app.myfincontrol.data.enums.TransactionCategories
import com.app.myfincontrol.data.enums.TransactionType
import java.math.BigDecimal


sealed class TransactionEvents {
    class AddTransaction(val type: TransactionType, val category: TransactionCategories, val amount: BigDecimal, val note: String) : TransactionEvents()

    class DeleteTransaction(val id: Int): TransactionEvents()
}