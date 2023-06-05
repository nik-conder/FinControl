package com.app.myfincontrol.data.sources.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.myfincontrol.data.entities.Profile

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProfile(profile: Profile)

    @Query("SELECT * FROM profile")
    fun getProfiles(): List<Profile>

}