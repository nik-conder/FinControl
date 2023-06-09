package com.app.myfincontrol.data.repositories

import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.sources.database.ProfileDao
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) {

    suspend fun createProfile(profile: Profile): Long {
        return profileDao.insertProfile(profile = profile)
    }

    fun getProfiles(): List<Profile> {
        return profileDao.getProfiles()
    }

    fun getProfile(uid: Int): Profile {
        return profileDao.getProfile(uid = uid)
    }

}