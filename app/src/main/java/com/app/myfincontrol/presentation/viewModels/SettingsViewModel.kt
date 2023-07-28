package com.app.myfincontrol.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.myfincontrol.domain.useCases.ProfileUseCase
import com.app.myfincontrol.domain.useCases.SessionUseCase
import com.app.myfincontrol.presentation.viewModels.events.SettingsEvents
import com.app.myfincontrol.presentation.viewModels.states.SettingsStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sessionUseCase: SessionUseCase,
    private val profileUseCase: ProfileUseCase,
) : ViewModel() {

    private val _states = MutableStateFlow(SettingsStates())
    val states = _states.asStateFlow()

    init {
        viewModelScope.launch {
            loading()
        }
    }

    private suspend fun loading() {
        getAuthProfile()
    }

    private suspend fun getAuthProfile() = profileUseCase.getAuthProfile().collect() { profile ->
        _states.update { it.copy(selectedProfile = profile) }
    }


    fun onEvents(event: SettingsEvents) {
        when (event) {
            is SettingsEvents.DarkMode -> {
                Log.i("Dark mode", "Clicked")
            }

            is SettingsEvents.DeleteProfile -> {
                if (states.value.selectedProfile != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        profileUseCase.deleteProfile(profile = states.value.selectedProfile!!)
                    }
                    Log.i("Profile", "deleted")
                } else {
                    Log.i("Profile", "not selected")
                }
            }

            is SettingsEvents.Logout -> {
                if (states.value.selectedProfile != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        sessionUseCase.deleteSession()
                    }
                } else {
                    Log.i("Profile", "not selected")
                }
            }
        }
    }
}