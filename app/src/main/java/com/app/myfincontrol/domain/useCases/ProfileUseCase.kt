package com.app.myfincontrol.domain.useCases

import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.repositories.ProfileRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository

) {

    suspend fun createProfile(profile: Profile): Boolean {
        //profileRepository.createProfile(profile = profile)
        return false
    }

    fun getProfiles(): List<Profile> {
        return profileRepository.getProfiles()
    }

    fun getProfile(uid: Int): Profile {
        return profileRepository.getProfile(uid = uid)
    }
}