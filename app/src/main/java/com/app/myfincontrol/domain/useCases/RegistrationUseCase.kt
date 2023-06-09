package com.app.myfincontrol.domain.useCases

import android.util.Log
import com.app.myfincontrol.data.entities.Balance
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.repositories.BalanceRepository
import com.app.myfincontrol.data.repositories.ProfileRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val balanceRepository: BalanceRepository
) {
    suspend fun createProfile(profile: Profile): Boolean {
        return try {
            val profileId = profileRepository.createProfile(profile)
            balanceRepository.createBalance(Balance(profile_id = profileId.toInt()))
            true
        } catch (e: Exception) {
            Log.e("Registration", e.message.toString())
            false
        }
    }

    fun deleteProfile() {

    }
}