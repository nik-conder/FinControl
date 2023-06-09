package com.app.myfincontrol.data.sources.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.entities.Transaction

@Dao
interface TransactionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transaction: Transaction): Long

    @Query("SELECT * FROM `transaction`")
    suspend fun getAllTransactions(): List<Transaction>

    @Query("SELECT * FROM `transaction` WHERE id >= :lastID ORDER BY datetime DESC LIMIT :limit")
    suspend fun getTransactionById(lastID: Int, limit: Int): List<Transaction>

    @Query("DELETE FROM `transaction`")
    suspend fun deleteAllTransactions()

}