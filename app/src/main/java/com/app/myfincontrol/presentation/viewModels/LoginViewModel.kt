package com.app.myfincontrol.presentation.viewModels

import android.content.Context
import android.icu.math.BigDecimal
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.app.myfincontrol.data.entities.Balance
import com.app.myfincontrol.data.sources.dataStore.LoginDataStore
import com.app.myfincontrol.dataStore
import com.app.myfincontrol.domain.useCases.BalanceUseCase
import com.app.myfincontrol.domain.useCases.LoginUseCase
import com.app.myfincontrol.domain.useCases.ProfileUseCase
import com.app.myfincontrol.presentation.compose.navigation.Screen
import com.app.myfincontrol.presentation.viewModels.events.LoginEvents
import com.app.myfincontrol.presentation.viewModels.states.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val profileUseCase: ProfileUseCase,
    private val balanceUseCase: BalanceUseCase,
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val _states = MutableStateFlow(LoginState())
    val states = _states.asStateFlow()

    init {
        loading()
    }

    fun loading() {
        viewModelScope.launch {
            getProfiles()
            delay(2000)
            _states.update { it.copy(isLoading = true) }
        }
    }

    private fun getProfiles() {
        if (states.value.profilesList.isEmpty()) {
            viewModelScope.launch {
                val list = profileUseCase.getProfiles()
                if (list.isEmpty()) {
                    Toast.makeText(context, "Not profiles", Toast.LENGTH_SHORT).show()
                } else {
                    _states.update {
                        it.copy(
                            profilesList = list
                        )
                    }
                }
            }
        }
    }

    fun onEvents(event: LoginEvents) {
        when (event) {
            is LoginEvents.Login -> {
                if (!states.value.isLogin) {
                    _states.update { it.copy(isLogin = true, startDestination = Screen.Home.route) }

                    viewModelScope.launch {
                        loginUseCase.setLoggedProfile(event.profile.uid)
                        val profile = loginUseCase.getLoggedProfile()

                        context.dataStore.data.collect {
                            println(it)
                        }

                        println("logged: $profile")
                    }

                    Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Вы уже вошли", Toast.LENGTH_SHORT).show()
                }

            }

            is LoginEvents.NewProfile -> {
                _states.update { it.copy(startDestination = Screen.CreateProfile.route) }
            }

            is LoginEvents.CreateAccount -> {
                if (event.profile.name.isEmpty() || event.profile.currency.name.isEmpty()) {
                    Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()

                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        launch {
                            profileUseCase.createProfile(event.profile)
                        }.join()

                        launch {
                            val profilesList = profileUseCase.getProfiles()
                            _states.update { it.copy(profilesList = profilesList) }
                        }.join()

                        launch {
                            if (states.value.profilesList.isNotEmpty()) {
                                balanceUseCase.createBalance(balance =
                                Balance(profile_id = states.value.profilesList[0].uid, main_balance = BigDecimal("0.00"))
                                )
                            } else {
                                Toast.makeText(context, "Profile not created", Toast.LENGTH_SHORT).show()
                            }
                        }.join()
                    }

                    if (states.value.profilesList.isNotEmpty()) {
                        _states.update { it.copy(startDestination = Screen.Home.route) }
                        Toast.makeText(context, "Create Account", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Profile not created", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}