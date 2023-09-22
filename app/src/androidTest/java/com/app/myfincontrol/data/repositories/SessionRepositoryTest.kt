package com.app.myfincontrol.data.repositories

import com.app.myfincontrol.data.entities.Session
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class SessionRepositoryTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Inject
    lateinit var sessionRepository: SessionRepository

    @Test
    fun checkCreateSession() = runBlocking {
        val session = Session(uid = 1, profile_id = 1, timestamp = System.currentTimeMillis())
        val result = sessionRepository.setSession(session)
        println("result: $result")
        assertEquals(1L, result)
    }

    @Test
    fun getAllSessions() = runBlocking {
        //todo
    }

    @Test
    fun getLastSession() = runBlocking {
        val session = Session(uid = 1, profile_id = 1, timestamp = System.currentTimeMillis())
        sessionRepository.setSession(session)
        val result = sessionRepository.getLastSession()
        assertEquals(session, result)
    }

    @Test
    fun deleteAllSessions() = runBlocking {
        val listSessions = listOf(
            Session(uid = 1, profile_id = 1, timestamp = System.currentTimeMillis()),
            Session(uid = 2, profile_id = 1, timestamp = System.currentTimeMillis())
        )
        listSessions.apply {
            sessionRepository.setSession(this[0])
        }

        sessionRepository.deleteAllSessions()
        val result = sessionRepository.getLastSession()
        assertEquals(null, result)
    }
}