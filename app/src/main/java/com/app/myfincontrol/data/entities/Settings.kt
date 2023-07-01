package com.app.myfincontrol.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.myfincontrol.data.Configuration
import com.app.myfincontrol.data.enums.DarkMode

@Entity
data class Settings(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "profile_id") val profile_id: Int,
    @ColumnInfo(name = "darkMode") val darkMode: DarkMode = Configuration.Settings.DARK_MODE,
)