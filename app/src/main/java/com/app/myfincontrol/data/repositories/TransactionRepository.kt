package com.app.myfincontrol.data.repositories

import android.icu.math.BigDecimal
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.sources.FeedDataSource
import com.app.myfincontrol.data.sources.database.TransactionDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDAO: TransactionDAO,
    private val feedDataSource: FeedDataSource
) {
    suspend fun addTransaction(transactions: Transaction): Long {
        return transactionDAO.insertTransaction(transactions)
    }

    fun getAllTransactions(): FeedDataSource = feedDataSource

    suspend fun getBalance(profile_id: Int): Flow<BigDecimal> = transactionDAO.getBalance(profile_id)

}