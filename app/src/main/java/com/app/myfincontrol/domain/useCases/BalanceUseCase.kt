package com.app.myfincontrol.domain.useCases

import com.app.myfincontrol.data.entities.Balance
import com.app.myfincontrol.data.repositories.BalanceRepository
import javax.inject.Inject

class BalanceUseCase @Inject constructor(
    val balanceRepository: BalanceRepository
) {

    suspend fun createBalance(balance: Balance) {
        balanceRepository.createBalance(balance)
    }
}