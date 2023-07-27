package com.app.myfincontrol.presentation.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.sources.UserStore
import com.app.myfincontrol.domain.useCases.ProfileUseCase
import com.app.myfincontrol.domain.useCases.SessionUseCase
import com.app.myfincontrol.presentation.viewModels.events.SettingsEvents
import com.app.myfincontrol.presentation.viewModels.states.SettingsStates
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionUseCase: SessionUseCase,
    private val profileUseCase: ProfileUseCase,
    private val dataStore: UserStore
): ViewModel() {

    private val _states = MutableStateFlow(SettingsStates())
    val states = _states.asStateFlow()

    init {
        viewModelScope.launch {
            val session = sessionUseCase.getLastSession()
            if (session != null && session.profile_id > 0) {
                loading(session.profile_id)
            } else {
                // todo
            }
        }
    }

    private suspend fun loading(profile_id: Int) {
        val profile = loadingProfile(profile_id)
        _states.update {
            it.copy(
                selectedProfile = profile,
                isLoading = true
            )
        }
    }

    private fun loadingProfile(profile_id: Int): Profile {
        return profileUseCase.getProfile(profile_id)
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

                    Toast.makeText(context, "Profile deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Профиль не выбран", Toast.LENGTH_SHORT).show()
                }
            }

            is SettingsEvents.Logout -> {
                if (states.value.selectedProfile != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        sessionUseCase.deleteSession()
                    }
                } else {
                    Toast.makeText(context, "Профиль не выбран", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}