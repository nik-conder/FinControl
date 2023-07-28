package com.app.myfincontrol.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.myfincontrol.data.Configuration
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.entities.Session
import com.app.myfincontrol.data.sources.UserStore
import com.app.myfincontrol.domain.useCases.ProfileUseCase
import com.app.myfincontrol.domain.useCases.SessionUseCase
import com.app.myfincontrol.domain.useCases.ValidationResult
import com.app.myfincontrol.domain.useCases.ValidatorUseCase
import com.app.myfincontrol.presentation.compose.navigation.Screen
import com.app.myfincontrol.presentation.viewModels.events.LoginEvents
import com.app.myfincontrol.presentation.viewModels.states.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionUseCase: SessionUseCase,
    private val profileUseCase: ProfileUseCase,
    private val validatorUseCase: ValidatorUseCase,
) : ViewModel() {

    private val _states = MutableStateFlow(LoginState())
    val states = _states.asStateFlow()

    init {
        viewModelScope.launch {
            loading()
        }
    }

    fun onEvents(event: LoginEvents) {
        when (event) {

            is LoginEvents.Login -> CoroutineScope(Dispatchers.IO).launch {
                setSession(states.value.selectedProfile)
            }

            is LoginEvents.SelectProfile -> CoroutineScope(Dispatchers.IO).launch {
                selectProfile(event.uid)
            }

            is LoginEvents.CreateAccount -> createAccount(event.profile)

        }
    }

    private suspend fun loading() {
        val sessionIdFlow = sessionIdFlow()
        val profilesFlow = getProfiles()

        combine(sessionIdFlow, profilesFlow) { sessionId, profiles ->

            if (sessionId > 0) {
                _states.value = _states.value.copy(
                    selectedProfile = sessionId
                )
            }

            if (profiles.isNotEmpty()) {
                _states.value = _states.value.copy(
                    profilesList = profiles
                )
            }

            _states.value = _states.value.copy(
                isLoading = true
            )

        }.collect()
    }

    private suspend fun sessionIdFlow(): Flow<Int> {
        return sessionUseCase.getAllSessions()
            .map { sessions -> if (sessions.isNotEmpty()) sessions.first().profile_id else -1 }
    }

    private fun getProfiles(): Flow<List<Profile>> {
        return profileUseCase.getProfiles()
    }

    private suspend fun selectProfile(id: Int) {
        val a = sessionUseCase.setSession(Session(profile_id = id, timestamp = System.currentTimeMillis()))
        println(a)
    }

    private suspend fun setSession(profileId: Int): Long = coroutineScope {
        val result = async {
            sessionUseCase.setSession(
                Session(
                    profile_id = profileId,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
        result.await()
    }

    private fun createAccount(profile: Profile) {
        if (states.value.profilesList.size <= Configuration.Limits.LIMIT_PROFILES) {
            val validationResult = validatorUseCase.validation(profile)
            if (validationResult is ValidationResult.Success) {
                CoroutineScope(Dispatchers.IO).launch {
                    profileUseCase.createProfile(
                        Profile(
                            name = profile.name,
                            currency = profile.currency
                        )
                    )
                    val getProfile = profileUseCase.getLastProfile()
                    val session = setSession(getProfile.uid)
                    if (session > 0) {
                        _states.update {
                            it.copy(
                                startDestination = Screen.Home.route,
                                selectedProfile = getProfile.uid
                            )
                        }
                    }
                }
            }
        } else {
            Log.e("Error", "Limit of profiles exceeded")
        }
    }
}