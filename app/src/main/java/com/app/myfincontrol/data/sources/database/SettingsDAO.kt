package com.app.myfincontrol.data.sources.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.app.myfincontrol.data.entities.Settings

@Dao
interface SettingsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSettings(settings: Settings)
}