package com.app.myfincontrol.data.sources.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.myfincontrol.data.entities.Transactions

@Dao
interface TransactionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transactions: Transactions): Long

    @Query("SELECT * FROM `transactions`")
    suspend fun getAllTransactions(): List<Transactions>

    @Query("SELECT * FROM `transactions` WHERE id > :lastID ORDER BY datetime DESC LIMIT :limit")
    suspend fun getTransactions(lastID: Int, limit: Int): List<Transactions>

    @Query("DELETE FROM `transactions`")
    suspend fun deleteAllTransactions()

}