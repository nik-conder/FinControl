package com.app.myfincontrol.data.repositories

import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.sources.FeedDataSource
import com.app.myfincontrol.data.sources.database.TransactionDAO
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDAO: TransactionDAO,
    private val feedDataSource: FeedDataSource
) {
    suspend fun addTransaction(transaction: Transaction): Long {
        return transactionDAO.insertTransaction(transaction)
    }

    fun getAllTransactions(): FeedDataSource = feedDataSource

}