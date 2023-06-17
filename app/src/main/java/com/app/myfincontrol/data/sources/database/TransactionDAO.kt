package com.app.myfincontrol.data.sources.database

import android.icu.math.BigDecimal
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.myfincontrol.data.entities.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transactions: Transaction): Long

    @Query("SELECT * FROM `Transaction`")
    suspend fun getAllTransactions(): List<Transaction>

    @Query("SELECT * FROM `transaction` WHERE id > :lastID ORDER BY datetime ASC LIMIT :limit")
    suspend fun getTransactions(lastID: Int, limit: Int): List<Transaction>

    @Query ("SELECT COALESCE(SUM(CASE WHEN type = 'INCOME' THEN amount ELSE -amount END), 0) as balance FROM `transaction` WHERE profile_id = :profile_id")
    fun getBalance(profile_id: Int): Flow<BigDecimal>

//    @Query("SELECT * FROM transactions WHERE id > :lastID ORDER BY id ASC LIMIT :limit OFFSET :offset")
//    fun getTransactionsWithOffset(lastID: Long, limit: Int, offset: Int): List<Transactions>


    @Query("DELETE FROM `transaction`")
    suspend fun deleteAllTransactions()

    @Query("SELECT id FROM `transaction` ORDER BY id ASC LIMIT 1")
    suspend fun getLastID(): Long

    @Query("SELECT id FROM `transaction` WHERE id > :currentID ORDER BY id  ASC LIMIT 1")
    suspend fun getNextID(currentID: Int): Long

}