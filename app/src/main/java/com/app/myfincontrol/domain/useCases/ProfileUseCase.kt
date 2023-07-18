package com.app.myfincontrol.domain.useCases

import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.repositories.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository

) {

    fun getProfiles(): Flow<List<Profile>> {
        return profileRepository.getProfiles()
    }

    fun getProfile(uid: Int): Profile {
        return profileRepository.getProfile(uid = uid)
    }
    suspend fun deleteProfile(profile: Profile) {
        return profileRepository.deleteProfile(profile = profile)
    }

    fun getLastProfile(): Profile {
        return profileRepository.getLastProfile()
    }

    suspend fun createProfile(profile: Profile): Long {
        return profileRepository.createProfile(profile = profile)
    }
}