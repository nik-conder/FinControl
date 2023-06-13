package com.app.myfincontrol.domain.useCases

import com.app.myfincontrol.data.entities.Transactions
import com.app.myfincontrol.data.repositories.BalanceRepository
import com.app.myfincontrol.data.repositories.TransactionRepository
import javax.inject.Inject

class TransactionUseCase @Inject constructor(
    private val balanceRepository: BalanceRepository,
    private val transactionRepository: TransactionRepository
) {

    suspend fun addTransaction(transactions: Transactions) {
        transactionRepository.addTransaction(transactions)
    }

    fun getAllTransactions() = transactionRepository.getAllTransactions()

}