package com.app.myfincontrol.presentation.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.myfincontrol.dataStore
import com.app.myfincontrol.domain.useCases.LoginUseCase
import com.app.myfincontrol.presentation.viewModels.events.HomeEvents
import com.app.myfincontrol.presentation.viewModels.events.LoginEvents
import com.app.myfincontrol.presentation.viewModels.states.HomeStates
import com.app.myfincontrol.presentation.viewModels.states.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginUseCase: LoginUseCase,
): ViewModel() {

    private val _states = MutableStateFlow(HomeStates())
    val states = _states.asStateFlow()

    val test = "abcd"
    init {
        println("viewModel created")
    }

    fun onEvents(event: HomeEvents) {
        when (event) {
            is HomeEvents.Login -> {
                _states.update { it.copy(selectedProfile = event.profile) }
            }
        }
    }
}