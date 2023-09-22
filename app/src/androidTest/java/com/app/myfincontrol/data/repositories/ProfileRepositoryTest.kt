package com.app.myfincontrol.data.repositories

import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.enums.Currency
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ProfileRepositoryTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Inject
    lateinit var profileRepository: ProfileRepository


    @Test
    fun checkCreateProfile() = runBlocking {
        val profile = Profile(uid = 1, name = "account #1", currency = Currency.USD)
        val result = profileRepository.createProfile(profile)
        println("result: $result")
        assertEquals(1L, result)
    }

    @Test
    fun getProfiles() = runBlocking {

        val listProfiles = listOf(
            Profile(uid = 1, name = "account #1", currency = Currency.USD),
            Profile(uid = 2, name = "account #2", currency = Currency.EUR),
            Profile(uid = 3, name = "account #3", currency = Currency.RUB)
        )

        profileRepository.createProfile(listProfiles[0])
        profileRepository.createProfile(listProfiles[1])
        profileRepository.createProfile(listProfiles[2])

        profileRepository.getProfiles().take(listProfiles.size).first().let {
            assertEquals(listProfiles, it)
        }
    }

    @Test
    fun getProfile() = runBlocking {
        val profile = Profile(uid = 1, name = "account #1", currency = Currency.USD)
        profileRepository.createProfile(profile)
        val getProfile = profileRepository.getProfile(1)
        assertEquals(1, getProfile.first().uid)
    }

    @Test
    fun deleteProfile() = runBlocking {
        val profile = Profile(uid = 1, name = "account #1", currency = Currency.USD)
        profileRepository.createProfile(profile)
        profileRepository.deleteProfile(profile)
        val result = profileRepository.getProfile(1).first()
        assertEquals(result, null)
    }

    @Test
    fun getLastProfile() = runBlocking {
        val profile = Profile(uid = 1, name = "account #1", currency = Currency.USD)
        profileRepository.createProfile(profile)
        val result = profileRepository.getLastProfile()
        assertEquals(profile, result)
    }

    @Test
    fun getAuthProfile() {
        //todo
    }
}