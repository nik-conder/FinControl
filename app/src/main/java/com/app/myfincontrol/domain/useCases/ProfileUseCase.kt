package com.app.myfincontrol.domain.useCases

import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.repositories.ProfileRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,

) {

    suspend fun createProfile(profile: Profile) {
        return profileRepository.createProfile(profile = profile)
    }

    fun getProfiles(): List<Profile> {
        return profileRepository.getProfiles()
    }
}