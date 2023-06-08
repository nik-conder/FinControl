package com.app.myfincontrol.data.sources.dataStore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.app.myfincontrol.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun setLogin(isLogin: Boolean) {
        context.dataStore.edit {
            it[booleanPreferencesKey("isLogin")] = isLogin
        }
    }

    fun getLogin() = context.dataStore.data.map {
        it[booleanPreferencesKey("isLogin")] ?: false
    }

    suspend fun setLoggedProfile(uid: Int) {
        context.dataStore.edit {
            it[intPreferencesKey("loggedProfile")] = uid
        }
    }

    fun getLoggedProfile(): Flow<Int> {
        return context.dataStore.data.map {
            it[intPreferencesKey("loggedProfile")] ?: 0
        }
    }
}