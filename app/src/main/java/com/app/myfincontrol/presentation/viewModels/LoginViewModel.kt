package com.app.myfincontrol.presentation.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.app.myfincontrol.data.Configuration
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.entities.Session
import com.app.myfincontrol.domain.useCases.ProfileUseCase
import com.app.myfincontrol.domain.useCases.RegistrationUseCase
import com.app.myfincontrol.domain.useCases.SessionUseCase
import com.app.myfincontrol.presentation.compose.navigation.Screen
import com.app.myfincontrol.presentation.viewModels.events.LoginEvents
import com.app.myfincontrol.presentation.viewModels.states.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionUseCase: SessionUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val profileUseCase: ProfileUseCase,
): ViewModel() {

    private val _states = MutableStateFlow(LoginState())
    val states = _states.asStateFlow()

    private val navController: NavController = NavController(context)

    init {
        loading()
    }

    private fun loading() {
        viewModelScope.launch {

            val listProfiles = profileUseCase.getProfiles()
            if (listProfiles.isNotEmpty()) {
                _states.update { it.copy(isLoading = true, profilesList = listProfiles) }
                autoSelectProfile()
            } else {
                _states.update { it.copy(isLoading = true, startDestination = Screen.CreateProfile.route) }
            }

        }
    }

    private fun autoSelectProfile() {
        viewModelScope.launch {
            val lastSession = sessionUseCase.getAllSession().first()
            if (lastSession.isNotEmpty()) {
                _states.update {
                    it.copy(selectedProfile = lastSession.first().profile_id)
                }
            }
        }
    }

//    private suspend fun checkSessions(): List<Session> {
//        return sessionUseCase.getAllSession()
//    }


    fun onEvents(event: LoginEvents) {
        when (event) {
            is LoginEvents.Login -> {
                viewModelScope.launch {
                    sessionUseCase.setSession(Session(profile_id = states.value.selectedProfile, timestamp = System.currentTimeMillis()))
                }
                _states.update {
                    it.copy(startDestination = Screen.Home.route)
                }
            }

            is LoginEvents.SelectProfile -> {
                Toast.makeText(context, "Select profile ${event.uid}", Toast.LENGTH_SHORT).show()
                _states.update {
                    it.copy(selectedProfile = event.uid)
                }
            }

            is LoginEvents.CreateAccount -> {

                if (states.value.profilesList.size <= Configuration.Limits.LIMIT_PROFILES) {
                    if (event.profile != null) {
                        if (event.profile.uid > 0 || event.profile.name.isNotEmpty()) {

                            CoroutineScope(Dispatchers.IO).launch {
                                val result: Boolean = withContext(Dispatchers.IO) { registrationUseCase.createProfile(
                                    Profile(name = event.profile.name, currency = event.profile.currency)
                                ) }

                                if (result) {
                                    _states.update { it.copy(startDestination = Screen.Home.route) }
                                } else {
                                    this.cancel()
                                    Toast.makeText(context, "Не удалось создать", Toast.LENGTH_SHORT).show()
                                }
                            }

                        } else {
                            Toast.makeText(context, "Не удалось создать", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Limit", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}