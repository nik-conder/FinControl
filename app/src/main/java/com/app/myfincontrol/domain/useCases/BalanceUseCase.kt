package com.app.myfincontrol.domain.useCases

import android.icu.math.BigDecimal
import com.app.myfincontrol.data.entities.Balance
import com.app.myfincontrol.data.repositories.BalanceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BalanceUseCase @Inject constructor(
    private val balanceRepository: BalanceRepository
) {

    suspend fun createBalance(balance: Balance) {
        balanceRepository.createBalance(balance)
    }


    suspend fun getBalance(profile_id: Int): Flow<BigDecimal> {
        return balanceRepository.getBalance(profile_id)
    }
}