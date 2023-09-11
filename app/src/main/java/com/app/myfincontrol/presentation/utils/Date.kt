package com.app.myfincontrol.presentation.utils

import android.icu.text.SimpleDateFormat
import java.util.Locale

object Date: UtilsCompose {
    fun formatDate(date: Long): String {
        val sdf = SimpleDateFormat("dd.MM.yy HH:mm:ss", Locale.getDefault())
        return sdf.format(date)
    }
}