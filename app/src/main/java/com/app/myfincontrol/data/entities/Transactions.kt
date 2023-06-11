package com.app.myfincontrol.data.entities

import android.icu.math.BigDecimal
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.myfincontrol.data.TransactionType

    @Entity
    data class Transactions(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "type") val type: TransactionType,
        @ColumnInfo(name = "amount") val amount: BigDecimal = BigDecimal("0.00"),
        @ColumnInfo(name = "datetime") val datetime: Long,
        @ColumnInfo(name = "category") val category: String
    )