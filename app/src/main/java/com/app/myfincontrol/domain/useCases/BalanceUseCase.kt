package com.app.myfincontrol.domain.useCases

import android.icu.math.BigDecimal
import android.util.Log
import com.app.myfincontrol.data.entities.Balance
import com.app.myfincontrol.data.repositories.BalanceRepository
import com.app.myfincontrol.data.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BalanceUseCase @Inject constructor(
    private val balanceRepository: BalanceRepository,
    private val transactionRepository: TransactionRepository
) {

    suspend fun createBalance(balance: Balance) {
        balanceRepository.createBalance(balance)
    }


    suspend fun getBalance(profile_id: Int): Flow<BigDecimal> {
        return try {
            transactionRepository.getBalance(profile_id)
        } catch (e: Exception) {
            Log.d("BalanceUseCase", e.toString())
            flow { BigDecimal.ZERO }
        }
    }
}