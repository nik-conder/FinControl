package com.app.myfincontrol.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.myfincontrol.data.enums.Currency

@Entity
data class Profile(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "currency") val currency: Currency
)