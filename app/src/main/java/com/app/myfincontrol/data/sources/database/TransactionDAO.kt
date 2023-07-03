package com.app.myfincontrol.data.sources.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.enums.TransactionType
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
interface TransactionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transactions: Transaction): Long

    @Query("SELECT t.* FROM `transaction` t INNER JOIN (SELECT profile_id, uid FROM `Session` ORDER BY uid DESC LIMIT 1) s ON t.profile_id = s.profile_id WHERE t.id < :lastID AND s.profile_id = t.profile_id ORDER BY t.datetime DESC LIMIT :limit")
    suspend fun getTransactions(lastID: Long, limit: Int): List<Transaction>

    @Query ("SELECT COALESCE(SUM(CASE WHEN type = 'INCOME' THEN amount ELSE -amount END), 0) as balance FROM `transaction` WHERE profile_id = :profile_id")
    fun getBalance(profile_id: Int): Flow<BigDecimal>
    @Query("DELETE FROM `transaction`")
    suspend fun deleteAllTransactions()

    @Query("SELECT id FROM `transaction` ORDER BY id DESC LIMIT 1")
    suspend fun getLastID(): Long?

    @Query("SELECT id FROM `transaction` WHERE id > :currentID ORDER BY id LIMIT 1")
    suspend fun getNextID(currentID: Int): Long

    @Query("SELECT t.* FROM `transaction` t " +
            "INNER JOIN (SELECT profile_id, uid FROM `Session` ORDER BY uid DESC LIMIT 1) s ON t.profile_id = s.profile_id " +
            "WHERE type = :type AND datetime >= :startOfMonth AND datetime <= :startOfNextMonth")
    fun getIncomesForCurrentMonth(type: TransactionType = TransactionType.INCOME, startOfMonth: Long, startOfNextMonth: Long): List<Transaction>

}