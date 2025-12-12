package com.example.healifyapp


import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.After

class UserPreferencesTest {

    private val TEST_EMAIL = "test@example.com"
    private val TEST_PASSWORD = "123456"
    private val TEST_NAME = "Gül Pınar"

    private lateinit var mockUserPrefs: UserPreferences

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        mockUserPrefs = UserPreferences(context)
        // Her test başlangıcında DataStore'u temizleyerek bağımsız bir ortam sağlar.
        runBlocking { mockUserPrefs.clearUser() }
    }

    // Her testten sonra da temizleme, diğer testleri etkilememek için.
    @After
    fun tearDown() = runBlocking {
        // Test sonunda verileri temizleyerek DataStore'un orijinal haline dönmesini sağlar.
        mockUserPrefs.clearUser()
    }

    /**
     * Kullanıcı bilgilerinin (email, şifre, isim) DataStore'a başarılı bir şekilde kaydedilip
     * kaydedilmediğini ve doğru bir şekilde geri okunup okunmadığını test eder.
     */
    @Test
    fun testUserSavedAndReadCorrectly() = runBlocking {

        mockUserPrefs.saveUser(TEST_EMAIL, TEST_PASSWORD, TEST_NAME)

        val storedEmail = mockUserPrefs.getEmail.first()
        val storedPass = mockUserPrefs.getPassword.first()
        val storedName = mockUserPrefs.getName.first()

        assertEquals(TEST_EMAIL, storedEmail)
        assertEquals(TEST_PASSWORD, storedPass)
        assertEquals(TEST_NAME, storedName)
    }

    /**
     * Başlangıçta, herhangi bir kullanıcı verisi kaydedilmemişken,
     * oturumun kapalı (isLoggedIn = false) olarak algılanıp algılanmadığını test eder.
     */
    @Test
    fun testIsLoggedInStartsAsFalse() = runBlocking {
        assertEquals(false, mockUserPrefs.isLoggedIn.first())
    }

    /**
     * Kullanıcı bilgileri DataStore'a kaydedildikten hemen sonra,
     * oturum durumunun açık (isLoggedIn = true) olarak değişip değişmediğini test eder.
     */
    @Test
    fun testIsLoggedInBecomesTrueAfterSave() = runBlocking {
        mockUserPrefs.saveUser(TEST_EMAIL, TEST_PASSWORD, TEST_NAME)
        // Kullanıcı kaydedildikten sonra isLoggedIn'in true olduğunu test eder.
        assertEquals(true, mockUserPrefs.isLoggedIn.first())
    }
}