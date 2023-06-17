package com.app.myfincontrol.domain.useCases

import android.icu.math.BigDecimal
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.repositories.BalanceRepository
import com.app.myfincontrol.data.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionUseCase @Inject constructor(
    private val balanceRepository: BalanceRepository,
    private val transactionRepository: TransactionRepository
) {

    suspend fun addTransaction(transactions: Transaction) {
        transactionRepository.addTransaction(transactions)
    }

    fun getAllTransactions() = transactionRepository.getAllTransactions()

    suspend fun getBalance(profile_id: Int): Flow<BigDecimal> = transactionRepository.getBalance(profile_id)

}