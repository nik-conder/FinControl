package com.app.myfincontrol

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {

    companion object {
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
        private val HIDE_BALANCE = booleanPreferencesKey("hide_balance")
    }

    val getDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE] ?: false
    }

    val hideBalance : Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[HIDE_BALANCE] ?: false
    }

    suspend fun setDarkMode() {
        val currentDarkMode = getDarkMode.first()
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE] = !currentDarkMode
        }
    }

    suspend fun setHideBalance() {
        val currentHideBalance = hideBalance.first()
        context.dataStore.edit { preferences ->
            preferences[HIDE_BALANCE] = !currentHideBalance
        }
    }
}