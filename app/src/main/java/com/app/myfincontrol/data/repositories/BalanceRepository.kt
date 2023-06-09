package com.app.myfincontrol.data.repositories

import android.icu.math.BigDecimal
import com.app.myfincontrol.data.entities.Balance
import com.app.myfincontrol.data.sources.database.BalanceDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BalanceRepository @Inject constructor(
    private val balanceDao: BalanceDao
) {

    suspend fun createBalance(balance: Balance) {
        balanceDao.insertBalance(balance)
    }

    suspend fun getBalance(profile_id: Int): Flow<BigDecimal> {
        return balanceDao.getBalance(profile_id)
    }

}