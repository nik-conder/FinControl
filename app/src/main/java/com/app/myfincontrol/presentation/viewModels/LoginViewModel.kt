package com.app.myfincontrol.presentation.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.app.myfincontrol.data.Configuration
import com.app.myfincontrol.data.ErrorCode
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.entities.Session
import com.app.myfincontrol.domain.useCases.ProfileUseCase
import com.app.myfincontrol.domain.useCases.SessionUseCase
import com.app.myfincontrol.presentation.compose.navigation.Screen
import com.app.myfincontrol.presentation.viewModels.events.LoginEvents
import com.app.myfincontrol.presentation.viewModels.states.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionUseCase: SessionUseCase,
    private val profileUseCase: ProfileUseCase,
): ViewModel() {

    private val _states = MutableStateFlow(LoginState())
    val states = _states.asStateFlow()

    private val navController: NavController = NavController(context)

    interface ValidationResult {
        data class Success(val code: Int, val message: String) : ValidationResult
        data class Error(val code: ErrorCode, val message: String) : ValidationResult
    }

    sealed class Validator {
        interface Profile {
            companion object {
                fun validation(profile: com.app.myfincontrol.data.entities.Profile): ValidationResult {
                   if (profile.name.isEmpty()) {
                        return ValidationResult.Error(ErrorCode.EMPTY_PROFILE_NAME, "Название пустое")
                    } else if (profile.name.length > Configuration.Limits.LIMIT_CHARS_NAME_PROFILE) {
                       return ValidationResult.Error(ErrorCode.LONG_PROFILE_NAME, "Название слишком длинное")
                    } else {
                        return ValidationResult.Success(0, "Ok")
                    }
                }
            }
        }
    }

    init {
        getProfiles()
        autoSelectProfile()
    }
    private fun getProfiles() {
        CoroutineScope(Dispatchers.IO).launch {
            val listProfiles = profileUseCase.getProfiles()
            listProfiles.collect() { list ->
                _states.update { it.copy(profilesList = list) }
            }
        }
    }

    private fun autoSelectProfile() {
        val lastSession = sessionUseCase.getLastSession()
        if (lastSession != null) {
            if (lastSession.profile_id > 0) _states.update { it.copy(selectedProfile = lastSession.profile_id) }
        }
        _states.update {
            it.copy(isLoading = true)
        }
    }

    private suspend fun setSession(profile_id: Int): Long {
        val session = sessionUseCase.setSession(Session(profile_id = profile_id, timestamp = System.currentTimeMillis()))
        if (session > 0) {
            _states.update {
                it.copy(startDestination = Screen.Home.route)
            }
        }
        return session
    }

    fun onEvents(event: LoginEvents) {
        when (event) {
            is LoginEvents.Login -> {
                CoroutineScope(Dispatchers.IO).launch {
                    setSession(states.value.selectedProfile)
                }
            }

            is LoginEvents.SelectProfile -> {
                _states.update {
                    it.copy(selectedProfile = event.uid)
                }
            }

            is LoginEvents.CreateAccount -> {

                if (states.value.profilesList.size <= Configuration.Limits.LIMIT_PROFILES) {
                    val validationResult  = Validator.Profile.validation(event.profile)
                    if (validationResult is ValidationResult.Success) {
                        viewModelScope.launch {
                            profileUseCase.createProfile(Profile(name = event.profile.name, currency = event.profile.currency))
                            val getProfile = profileUseCase.getLastProfile()
                            val session = setSession(getProfile.uid)
                            if (session > 0) {
                                _states.update { it.copy(
                                    startDestination = Screen.Home.route,
                                    selectedProfile = getProfile.uid
                                ) }
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "Limit", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}