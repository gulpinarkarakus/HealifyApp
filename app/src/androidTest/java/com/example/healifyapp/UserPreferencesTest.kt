package com.example.healifyapp

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserPreferencesTest {

    private val TEST_EMAIL = "test@example.com"
    private val TEST_PASSWORD = "123456"
    private val TEST_NAME = "Gül Pınar"

    private lateinit var mockUserPrefs: UserPreferences

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        mockUserPrefs = UserPreferences(context)
    }

    @Test
    fun testUserSavedAndReadCorrectly() = runBlocking {

        // 1️⃣ DataStore'a yaz
        mockUserPrefs.saveUser(TEST_EMAIL, TEST_PASSWORD, TEST_NAME)

        // 2️⃣ DataStore'dan geri oku
        val storedEmail = mockUserPrefs.getEmail.first()
        val storedPass = mockUserPrefs.getPassword.first()
        val storedName = mockUserPrefs.getName.first()

        // 3️⃣ Beklenen sonuçlar
        assertEquals(TEST_EMAIL, storedEmail)
        assertEquals(TEST_PASSWORD, storedPass)
        assertEquals(TEST_NAME, storedName)
    }
}
