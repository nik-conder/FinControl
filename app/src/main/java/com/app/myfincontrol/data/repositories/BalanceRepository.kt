package com.app.myfincontrol.data.repositories

import android.icu.math.BigDecimal
import com.app.myfincontrol.data.entities.Balance
import com.app.myfincontrol.data.sources.database.DAO.BalanceDao
import javax.inject.Inject

class BalanceRepository @Inject constructor(
    private val balanceDao: BalanceDao
) {

    suspend fun createBalance(balance: Balance) {
        balanceDao.insertBalance(balance)
    }
}