package com.app.myfincontrol.domain.useCases

import java.math.BigDecimal
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.repositories.BalanceRepository
import com.app.myfincontrol.data.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    suspend fun addTransaction(transactions: Transaction): Long {
        return transactionRepository.addTransaction(transactions)
    }
    suspend fun deleteTransaction(id: Int) {
        return transactionRepository.deleteTransaction(id)
    }
}