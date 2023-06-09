package com.app.myfincontrol.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Session(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "profile_id") val profile_id: Int,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
)