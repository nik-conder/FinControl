package com.app.myfincontrol.data.repositories

import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.sources.database.ProfileDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) {

    suspend fun createProfile(profile: Profile): Long {
        return profileDao.insertProfile(profile = profile)
    }

    fun getProfiles(): Flow<List<Profile>> {
        return profileDao.getProfiles()
    }

    fun getProfile(uid: Int): Flow<Profile> {
        return profileDao.getProfile(uid = uid)
    }

    suspend fun deleteProfile(profile: Profile) {
        return profileDao.deleteProfile(profile = profile)
    }

    fun getLastProfile(): Profile {
        return profileDao.getLastProfile()
    }

    fun getAuthProfile(): Flow<Profile> {
        return profileDao.getAuthProfile()
    }

}