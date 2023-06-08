package com.app.myfincontrol.domain.useCases

import com.app.myfincontrol.data.sources.dataStore.LoginDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginDataStore: LoginDataStore
) {
    suspend fun setLogin(isLogin: Boolean) {
        loginDataStore.setLogin(isLogin)
    }

    suspend fun getLogin(): Flow<Boolean> {
        return loginDataStore.getLogin()
    }

    suspend fun setLoggedProfile(uid: Int) {
        loginDataStore.setLoggedProfile(uid)
    }

    fun getLoggedProfile(): Flow<Int> {
        return loginDataStore.getLoggedProfile()
    }
}