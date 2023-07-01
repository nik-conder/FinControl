package com.app.myfincontrol.presentation.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.enums.TransactionCategories
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.data.sources.FeedDataSource
import com.app.myfincontrol.data.sources.database.TransactionDAO
import com.app.myfincontrol.domain.useCases.BalanceUseCase
import com.app.myfincontrol.domain.useCases.ProfileUseCase
import com.app.myfincontrol.domain.useCases.SessionUseCase
import com.app.myfincontrol.domain.useCases.TransactionUseCase
import com.app.myfincontrol.presentation.viewModels.events.HomeEvents
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents
import com.app.myfincontrol.presentation.viewModels.states.HomeStates
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionUseCase: SessionUseCase,
    private val profileUseCase: ProfileUseCase,
    private val balanceUseCase: BalanceUseCase,
    private val transactionUseCase: TransactionUseCase,
    private val transactionDAO: TransactionDAO
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
        println("viewModel created")
    }

    private suspend fun loading() {
        val session = sessionUseCase.getAllSession()
        session.collect() { item ->
            if (item.isNotEmpty()) {
                _states.update { it.copy(isLogin = true, isLoading = true, selectedProfile = profileUseCase.getProfile(item.first().profile_id)) }
                getBalance(item.first().profile_id)
            } else {
                _states.update { it.copy(isLogin = false) }
            }
        }


        if (states.value.selectedProfile != null) {
            if (states.value.selectedProfile!!.uid != 0) getBalance(states.value.selectedProfile!!.uid)
        }
    }

    private suspend fun getBalance(profile_id: Int) {
        balanceUseCase.getBalance(profile_id).collect() {newValue ->
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

    fun onEventsTransaction(event: TransactionEvents) {
        when (event) {

            is TransactionEvents.LoadTransactions -> {

            }

            is TransactionEvents.AddTransaction -> {
                if (states.value.selectedProfile != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        transactionUseCase.addTransaction(
                            Transaction(
                                profileId = states.value.selectedProfile!!.uid,
                                type = event.type,
                                amount = event.amount,
                                datetime = System.currentTimeMillis(),
                                category = event.category.toString()
                            )
                        )
                    }
                    Toast.makeText(context, "Transaction added", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }

            is TransactionEvents.GenerateEvents -> {
                val count = 10
                viewModelScope.launch {
                    val randType = (0..1).random()
                    repeat(count) {
                        transactionUseCase.addTransaction(
                            Transaction(
                                type = when (randType) {
                                    0 -> {
                                        TransactionType.EXPENSE
                                    } else -> {
                                        TransactionType.INCOME
                                    }
                                },
                                amount = BigDecimal.valueOf((0..100).random().toDouble()),
                                datetime = System.currentTimeMillis(),
                                profileId = states.value.selectedProfile!!.uid,
                                category = when (randType) {
                                    0 -> {
                                        TransactionCategories.ExpenseCategories.ENTERTAINMENT.name
                                    } else -> {
                                        TransactionCategories.IncomeCategories.INVESTMENTS.name
                                    }
                                }
                            )
                        )
                        println(it)
                    }
                }
                Toast.makeText(context, "Сгенерированно $count транзакции", Toast.LENGTH_SHORT).show()
            }
        }
    }
}