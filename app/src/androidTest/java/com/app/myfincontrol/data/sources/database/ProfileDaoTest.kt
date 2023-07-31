package com.app.myfincontrol.data.sources.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.myfincontrol.data.AppDatabase
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.entities.Session
import com.app.myfincontrol.data.enums.Currency
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ProfileDaoTest {
    private lateinit var profileDao: ProfileDao
    private lateinit var db: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        profileDao = db.profileDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun insertAndGetProfiles() = runBlocking {
        val profile = Profile(uid = 1, name = "account #1", currency = Currency.USD)
        profileDao.insertProfile(profile)

        val profiles = profileDao.getProfiles().first()
        assertEquals((profile in profiles), true)
        assertEquals(profile.name, profiles[0].name)
        assertEquals(profile.currency, profiles[0].currency)
        assertEquals(profile.uid, profiles[0].uid)
        println("Object: $profiles")
    }

    @Test
    @Throws(IOException::class)
    fun getLastProfile() = runBlocking {
        val profile1 = Profile(uid = 1, name = "account #1", currency = Currency.USD)
        val profile2 = Profile(uid = 2, name = "account #2", currency = Currency.EUR)
        profileDao.insertProfile(profile1)
        profileDao.insertProfile(profile2)

        val lastProfile = profileDao.getLastProfile()
        assertEquals(profile2 == lastProfile, true)
    }

    @Test
    fun getProfileById() = runBlocking {
        val profile = Profile(uid = 1, name = "account #1", currency = Currency.USD)
        profileDao.insertProfile(profile)

        val result = profileDao.getProfile(1).first()
        assertEquals(result, profile)
    }

    @Test
    fun deleteProfile() = runBlocking {
        val profile = Profile(uid = 1, name = "account #1", currency = Currency.USD)
        profileDao.insertProfile(profile)

        profileDao.deleteProfile(profile)
        val result = profileDao.getProfile(1).first()
        assertNull(result)
    }

    @Test
    fun getAuthProfile() = runBlocking {
        val profile = Profile(uid = 1, name = "account #1", currency = Currency.USD)
        profileDao.insertProfile(profile)

        val sessionForProfile1 = Session(profile_id = 1, timestamp = System.currentTimeMillis())
        db.sessionDao().insertSession(sessionForProfile1)

        val authProfile = profileDao.getAuthProfile().first()
        assertEquals(authProfile, profile)
    }
}