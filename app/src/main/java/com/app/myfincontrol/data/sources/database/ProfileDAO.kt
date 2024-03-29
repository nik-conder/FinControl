package com.app.myfincontrol.data.sources.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.app.myfincontrol.data.entities.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProfile(profile: Profile): Long

    @Query("SELECT * FROM profile")
    fun getProfiles(): Flow<List<Profile>>

    @Query("SELECT * FROM profile ORDER BY uid DESC LIMIT 1")
    fun getLastProfile(): Profile

    @Query("SELECT * FROM profile WHERE uid = :uid")
    fun getProfile(uid: Int): Flow<Profile>

    @Delete
    suspend fun deleteProfile(profile: Profile)

    @Query("SELECT p.* FROM `profile` p INNER JOIN (SELECT profile_id, uid FROM `Session` ORDER BY uid DESC LIMIT 1) s ON p.uid = s.profile_id")
    fun getAuthProfile(): Flow<Profile>
}
