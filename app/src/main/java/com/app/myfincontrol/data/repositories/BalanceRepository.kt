package com.app.myfincontrol.data.repositories

import com.app.myfincontrol.data.entities.Balance
import com.app.myfincontrol.data.sources.database.BalanceDao
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import javax.inject.Inject

class BalanceRepository @Inject constructor(
    private val balanceDao: BalanceDao
) {

    suspend fun createBalance(balance: Balance) {
        balanceDao.insertBalance(balance)
    }

}