package com.app.myfincontrol.data

interface Configuration {

    object Database: Configuration {
        const val DATABASE_NAME = "app-db"
    }

    object Limits: Configuration {
        const val LIMIT_PROFILES = 5
    }
}