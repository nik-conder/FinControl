package com.app.myfincontrol.domain.useCases

import android.util.Log
import com.app.myfincontrol.data.entities.Balance
import com.app.myfincontrol.data.repositories.BalanceRepository
import com.app.myfincontrol.data.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal
import javax.inject.Inject

class BalanceUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend fun getBalance(profile_id: Int): Flow<BigDecimal> {
        return try {
            transactionRepository.getBalance(profile_id)
        } catch (e: Exception) {
            Log.d("BalanceUseCase", e.toString())
            flow { BigDecimal.ZERO }
        }
    }
}