package com.app.myfincontrol.data.sources.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.myfincontrol.data.AppDatabase
import com.app.myfincontrol.data.entities.Session
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SessionDAOTest {
    lateinit var db: AppDatabase
    lateinit var sessionDao: SessionDAO

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        sessionDao = db.sessionDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun insertAndGetSession() = runBlocking {
        val session = Session(uid = 1, profile_id = 1, timestamp = System.currentTimeMillis())
        val createdSession = sessionDao.insertSession(session)
        assertTrue(createdSession >= 0)
        println("Created session: $createdSession")
        val sessions = sessionDao.getAllSessions().first()
        println("Sessions: $sessions")
        assertEquals((session in sessions), true)
        println("Find created session: ${(session in sessions)}")
    }

    @Test
    @Throws(IOException::class)
    fun deleteAllSessions() = runBlocking {

        repeat(10) {
            val session = Session(profile_id = 1, timestamp = System.currentTimeMillis())
            val result  = sessionDao.insertSession(session)
            assertTrue(result >= 0)
        }

        sessionDao.deleteAllSessions()
        val sessions = sessionDao.getAllSessions().first()
        assertEquals(sessions.isEmpty(), true)
    }

    @Test
    fun getAllSessions() = runBlocking {
        repeat(10) {
            val session = Session(profile_id = 1, timestamp = System.currentTimeMillis())
            val result  = sessionDao.insertSession(session)
            assertTrue(result >= 0)
        }
        val sessions = sessionDao.getAllSessions().first()
        assertNotNull(sessions)
        assertEquals(sessions.size, 10)
    }

    @Test
    fun getLastSession() = runBlocking {
        val session = Session(profile_id = 1, timestamp = System.currentTimeMillis())
        val result  = sessionDao.insertSession(session)
        assertTrue(result >= 0)
        val lastSession = sessionDao.getLastSession()
        assertNotNull(lastSession)
        assertEquals(lastSession.timestamp, session.timestamp)
    }

}