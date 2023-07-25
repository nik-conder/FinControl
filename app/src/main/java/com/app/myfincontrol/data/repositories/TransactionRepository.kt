package com.app.myfincontrol.data.repositories

import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.data.sources.FeedDataSource
import com.app.myfincontrol.data.sources.database.TransactionDAO
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDAO: TransactionDAO,
    private val feedDataSource: FeedDataSource
) {
    suspend fun addTransaction(transactions: Transaction): Long {
        return transactionDAO.insertTransaction(transactions)
    }

    fun getBalance(profile_id: Int, datetime: Long): Flow<BigDecimal> = transactionDAO.getBalance(profile_id, datetime)

    fun getChartTransactions(type: TransactionType, startTime: Long, endTime: Long): List<Transaction> {
        return transactionDAO.getChartTransactions(type, startTime, endTime)
    }

    fun getTransactions(): List<Transaction> {
        return transactionDAO.getTransactions()
    }

    suspend fun deleteTransaction(id: Int) {
        return transactionDAO.deleteTransaction(id)
    }
}