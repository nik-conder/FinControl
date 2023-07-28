package com.app.myfincontrol.domain.useCases

import android.util.Log
import com.app.myfincontrol.data.FormatDateImpl
import com.app.myfincontrol.data.enums.ChartSort
import com.app.myfincontrol.data.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal
import javax.inject.Inject

class BalanceUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    fun getBalance(sort: ChartSort): Flow<BigDecimal> {
        val datetime: Long = FormatDateImpl.getStartPeriod(sort)
        return try {
            transactionRepository.getBalance(datetime)
        } catch (e: Exception) {
            Log.d("BalanceUseCase", e.toString())
            flow { BigDecimal.ZERO }
        }
    }
}