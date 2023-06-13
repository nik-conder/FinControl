package com.app.myfincontrol.data.repositories

import com.app.myfincontrol.data.entities.Transactions
import com.app.myfincontrol.data.sources.FeedDataSource
import com.app.myfincontrol.data.sources.FeedDataSourceMediator
import com.app.myfincontrol.data.sources.database.TransactionDAO
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDAO: TransactionDAO,
    private val feedDataSource: FeedDataSource
) {
    suspend fun addTransaction(transactions: Transactions): Long {
        return transactionDAO.insertTransaction(transactions)
    }

    fun getAllTransactions(): FeedDataSource = feedDataSource

}