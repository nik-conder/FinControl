package com.app.myfincontrol.data

import com.app.myfincontrol.data.enums.DarkMode

interface Configuration {

    object Database: Configuration {
        const val DATABASE_NAME = "app-db"
    }

    object Limits: Configuration {
        const val LIMIT_PROFILES = 5
        const val LIMIT_CHARS_NAME_PROFILE = 32
        const val LIMIT_CHARS_NOTE = 64
    }

    object Settings: Configuration {
        val DARK_MODE: DarkMode = DarkMode.DISABLED
    }


}