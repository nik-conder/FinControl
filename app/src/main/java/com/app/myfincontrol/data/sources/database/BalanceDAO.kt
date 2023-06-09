package com.app.myfincontrol.data.sources.database

import android.icu.math.BigDecimal
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.myfincontrol.data.entities.Balance
import com.app.myfincontrol.data.entities.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBalance(balance: Balance)

    @Query("SELECT main_balance FROM balance WHERE profile_id = :profile_id")
    fun getBalance(profile_id: Int): Flow<BigDecimal>

//    @Query("SELECT * FROM balance")
//    fun getBalance(profile_id: Int): BigDecimal

}