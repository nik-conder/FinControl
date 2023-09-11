package com.app.myfincontrol.presentation.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.enums.ChartSort
import com.app.myfincontrol.data.enums.TransactionCategories
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.data.sources.FeedDataSource
import com.app.myfincontrol.data.sources.database.TransactionDAO
import com.app.myfincontrol.domain.useCases.BalanceUseCase
import com.app.myfincontrol.domain.useCases.ProfileUseCase
import com.app.myfincontrol.domain.useCases.TransactionUseCase
import com.app.myfincontrol.presentation.viewModels.events.DebugEvents
import com.app.myfincontrol.presentation.viewModels.events.HomeEvents
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents
import com.app.myfincontrol.presentation.viewModels.states.HomeStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val balanceUseCase: BalanceUseCase,
    private val transactionUseCase: TransactionUseCase,
    private val transactionDAO: TransactionDAO
) : ViewModel() {
    private val _states = MutableStateFlow(HomeStates())
    val states = _states.asStateFlow()

    val feedPager = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = true),
        pagingSourceFactory = { FeedDataSource(transactionDAO) }
    )


    init {

        viewModelScope.launch {
            loading()
        }
    }

    fun onEvents(event: HomeEvents) {
        when (event) {
            is HomeEvents.Login -> {
                _states.update { it.copy(selectedProfile = event.profile) }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEventsTransaction(event: TransactionEvents) {

        when (event) {
            is TransactionEvents.DeleteTransaction -> {
                CoroutineScope(Dispatchers.IO).launch {
                    transactionUseCase.deleteTransaction(event.id)
                }
            }

            is TransactionEvents.AddTransaction -> {
                addTransaction(event.type, event.amount, event.note, event.category)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEventDebugMode(event: DebugEvents) {
        when (event) {
            is DebugEvents.ShowAlertDebugMode -> {
                _states.update { it.copy(debugModeShow = !states.value.debugModeShow) }
            }

            is DebugEvents.GenerateTransactions -> {
                generateTransactions(event.type)
            }
        }
    }

    private suspend fun loading() = coroutineScope {
        launch {
            async { getAuthProfile() }
            async { getBalance() }
        }
    }

    private suspend fun getAuthProfile() {
        profileUseCase.getAuthProfile().collect { profile ->
            _states.update {
                it.copy(
                    selectedProfile = profile,
                    isLoading = true
                )
            }
        }
    }

    private suspend fun getBalance(sort: ChartSort = ChartSort.YEAR) {
        balanceUseCase.getBalance(sort).collect { balance ->
            println("Balance: $balance")
            _states.update { it.copy(balance = balance) }
        }
    }

    private fun addTransaction(
        type: TransactionType,
        amount: BigDecimal,
        note: String? = null,
        category: TransactionCategories
    ) {
        if (states.value.selectedProfile != null) {
            CoroutineScope(Dispatchers.IO).launch {
                transactionUseCase.addTransaction(
                    Transaction(
                        profileId = states.value.selectedProfile!!.uid,
                        type = type,
                        amount = amount,
                        datetime = System.currentTimeMillis() / 1000L,
                        category = category.toString(),
                        note = if (!note.isNullOrEmpty()) note else null
                    )
                )
            }

            Log.i("Transaction", " added")
        } else {
            Log.i("Transaction", " Error")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateTransactions(type: TransactionType) {
        var startDate = LocalDateTime.of(2023, 1, 1, 0, 0, 0).toLocalDate()

        repeat(206) {
            val date = startDate.plusDays(1)
            startDate = date
            val unixTimestamp = date.atStartOfDay(ZoneOffset.UTC).toEpochSecond()
            val transaction = Transaction(
                profileId = states.value.selectedProfile!!.uid,
                type = type,
                amount = (10..1000).random().toBigDecimal(),
                datetime = unixTimestamp,
                category = TransactionCategories.IncomeCategories.BUSINESS.name
            )
            println("==============")
            println(transaction)
            println(date)

            viewModelScope.launch {
                transactionUseCase.addTransaction(transaction)
            }

        }
    }
}