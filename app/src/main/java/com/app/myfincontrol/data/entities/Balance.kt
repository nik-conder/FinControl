package com.app.myfincontrol.data.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity

data class Balance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "profile_id") val profile_id: Int,
    @ColumnInfo(name = "main_balance") val main_balance: BigDecimal = BigDecimal("0.00")
)