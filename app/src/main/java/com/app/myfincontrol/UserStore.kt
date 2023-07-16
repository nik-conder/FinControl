package com.app.myfincontrol

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserStore @Inject constructor(
    @ApplicationContext private val context: Context
    ) {

    companion object {
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
        private val HIDE_BALANCE = booleanPreferencesKey("hide_balance")
        private val ADVICES_BOX = booleanPreferencesKey("advice_box")
        private val DEBUG_MODE = booleanPreferencesKey("debug_mode")
        //private val SESSION_ID = intPreferencesKey("session_id")
    }

    val darkModeState: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE] ?: false
    }

    val hideBalanceState : Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[HIDE_BALANCE] ?: false
    }

    val adviceBox : Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[ADVICES_BOX] ?: true
    }

    val debugModeState : Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DEBUG_MODE] ?: false
    }

//    val sessionId : Flow<Int> = context.dataStore.data.map { preferences ->
//        preferences[SESSION_ID] ?: 0
//    }

    suspend fun setDarkMode() {
        val currentDarkMode = darkModeState.first()
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE] = !currentDarkMode
        }
    }

    suspend fun sethideBalanceState() {
        val currenthideBalanceState = hideBalanceState.first()
        context.dataStore.edit { preferences ->
            preferences[HIDE_BALANCE] = !currenthideBalanceState
        }
    }

    suspend fun setAdviceBox() {
        val currentAdviceBox = adviceBox.first()
        context.dataStore.edit { preferences ->
            preferences[ADVICES_BOX] = !currentAdviceBox
        }
    }

    suspend fun setDebugMode() {
        val currentDebugMode = debugModeState.first()
        context.dataStore.edit { preferences ->
            preferences[DEBUG_MODE] = !currentDebugMode
        }
    }

//    suspend fun setSessionId() {
//        val currentSessionId = sessionId.first()
//        context.dataStore.edit { preferences ->
//            preferences[SESSION_ID] = currentSessionId
//        }
//    }
//
//    suspend fun clearSessionId() {
//        context.dataStore.edit { preferences ->
//            preferences[SESSION_ID] = 0
//        }
//    }
}