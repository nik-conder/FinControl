package com.app.myfincontrol.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.myfincontrol.data.enums.TransactionType
import java.math.BigDecimal

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "profile_id") val profileId: Int,
    @ColumnInfo(name = "type") val type: TransactionType,
    @ColumnInfo(name = "amount") val amount: BigDecimal = BigDecimal("0.00"),
    @ColumnInfo(name = "datetime") val datetime: Long,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "note") val note: String? = null
)