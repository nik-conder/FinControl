package com.app.myfincontrol.presentation.viewModels

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.enums.ChartSort
import com.app.myfincontrol.data.enums.TransactionCategories
import com.app.myfincontrol.data.sources.FeedDataSource
import com.app.myfincontrol.data.sources.UserStore
import com.app.myfincontrol.data.sources.database.TransactionDAO
import com.app.myfincontrol.dataStore
import com.app.myfincontrol.domain.useCases.BalanceUseCase
import com.app.myfincontrol.domain.useCases.ProfileUseCase
import com.app.myfincontrol.domain.useCases.SessionUseCase
import com.app.myfincontrol.domain.useCases.TransactionUseCase
import com.app.myfincontrol.presentation.viewModels.events.DebugEvents
import com.app.myfincontrol.presentation.viewModels.events.HomeEvents
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents
import com.app.myfincontrol.presentation.viewModels.states.HomeStates
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.Closeable
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionUseCase: SessionUseCase,
    private val profileUseCase: ProfileUseCase,
    private val balanceUseCase: BalanceUseCase,
    private val transactionUseCase: TransactionUseCase,
    private val transactionDAO: TransactionDAO,
    private val dataStore: UserStore
): ViewModel() {
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

    private suspend fun loading() {
        val session = sessionUseCase.getAllSessions()
        session.collect() { item ->
            if (item.isNotEmpty()) {
                _states.update { it.copy(isLogin = true, isLoading = true, selectedProfile = profileUseCase.getProfile(item.first().profile_id)) }
                getBalance(item.first().profile_id, ChartSort.YEAR) // todo ChartSort.DAY
            } else {
                _states.update { it.copy(isLogin = false) }
            }
        }

        if (states.value.selectedProfile != null) {
            if (states.value.selectedProfile!!.uid != 0) getBalance(dataStore.getSessionId(), ChartSort.YEAR) // todo ChartSort.DAY
        }
    }

    private suspend fun getBalance(profile_id: Int, sort: ChartSort) {
        balanceUseCase.getBalance(profile_id, sort).collect() { newValue ->
            _states.update { it.copy(balance = newValue) }
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
                Toast.makeText(context, "Transaction deleted", Toast.LENGTH_SHORT).show()
            }

            is TransactionEvents.AddTransaction -> {
                if (states.value.selectedProfile != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        transactionUseCase.addTransaction(
                            Transaction(
                                profileId = states.value.selectedProfile!!.uid,
                                type = event.type,
                                amount = event.amount,
                                datetime = System.currentTimeMillis() / 1000L,
                                category = event.category.toString()
                            )
                        )
                    }
                    Toast.makeText(context, "Transaction added", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
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
                var startDate = LocalDateTime.of(2023, 1, 1, 0, 0, 0).toLocalDate()

                repeat(206) {
                    val date = startDate.plusDays(1)
                    startDate = date
                    val unixTimestamp = date.atStartOfDay(ZoneOffset.UTC).toEpochSecond()
                    val transaction = Transaction(
                        profileId = states.value.selectedProfile!!.uid,
                        type = event.type,
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
    }
}