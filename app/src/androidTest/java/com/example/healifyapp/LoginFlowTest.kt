package com.example.healifyapp

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFlowTest {
//log TEST_LOG
    private val TAG = "TEST_LOG"

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    // 1) Dil butonları testi
    @Test
    fun testLanguageButtons() {
        Log.i(TAG, "testLanguageButtons başladı")

        try {
            onView(withId(R.id.btnTurkish)).perform(click())
            Log.i(TAG, "Türkçe butonuna tıklandı")

            onView(withText("Giriş Yap")).check(matches(isDisplayed()))
            Log.i(TAG, "Türkçe başlık görüntülendi")

            onView(withId(R.id.btnEnglish)).perform(click())
            Log.i(TAG, "İngilizce butonuna tıklandı")

            onView(withText("Sign In")).check(matches(isDisplayed()))
            Log.i(TAG, "İngilizce başlık görüntülendi")

            onView(withId(R.id.btnGerman)).perform(click())
            Log.i(TAG, "Almanca butonuna tıklandı")

            onView(withText("Anmelden")).check(matches(isDisplayed()))
            Log.i(TAG, "Almanca başlık görüntülendi")

            Log.i(TAG, "testLanguageButtons başarılı")

        } catch (e: Exception) {
            Log.e(TAG, "testLanguageButtons hata: ${e.message}")
            throw e
        }
    }

    // 2) Sayfa geçiş testi
    @Test
    fun testNavigateToSignUp() {
        Log.i(TAG, "testNavigateToSignUp başladı")

        try {
            onView(withId(R.id.btnSignup)).perform(click())
            Log.i(TAG, "Signup sayfasına geçiş yapıldı")

            onView(withText("Sign Up")).check(matches(isDisplayed()))
            Log.i(TAG, "'Sign Up' başlığı bulundu")

            Log.i(TAG, "testNavigateToSignUp başarılı")

        } catch (e: Exception) {
            Log.e(TAG, "testNavigateToSignUp hata: ${e.message}")
            throw e
        }
    }

    // 3) Login testi
    @Test
    fun testLoginButton() {
        Log.i(TAG, "testLoginButton başladı")

        try {
            onView(withId(R.id.etEmail)).perform(typeText("test@gmail.com"), closeSoftKeyboard())
            Log.i(TAG, "Email girildi")

            onView(withId(R.id.etPassword)).perform(typeText("123456"), closeSoftKeyboard())
            Log.i(TAG, "Password girildi")

            onView(withId(R.id.btnLogin)).perform(click())
            Log.i(TAG, "Login butonuna basıldı")

            onView(withText("Healify")).check(matches(isDisplayed()))
            Log.i(TAG, "Ana sayfa görüntülendi")

            Log.i(TAG, "testLoginButton başarılı")

        } catch (e: Exception) {
            Log.e(TAG, "testLoginButton hata: ${e.message}")
            throw e
        }
    }

    // Boş email hatası
    @Test
    fun testEmptyEmailShowsError() {
        Log.i(TAG, "testEmptyEmailShowsError başladı")

        try {
            onView(withId(R.id.etEmail)).perform(typeText(""), closeSoftKeyboard())
            Log.i(TAG, "Boş email alanı girildi")

            onView(withId(R.id.btnLogin)).perform(click())
            Log.i(TAG, "Login butonuna basıldı")

            onView(withText("Email cannot be empty")).check(matches(isDisplayed()))
            Log.i(TAG, "Boş email hatası görüntülendi")

            Log.i(TAG, "testEmptyEmailShowsError başarılı")

        } catch (e: Exception) {
            Log.e(TAG, "testEmptyEmailShowsError hata: ${e.message}")
            throw e
        }
    }

    // Geçersiz email hatası
    @Test
    fun testInvalidEmailShowsError() {
        Log.i(TAG, "testInvalidEmailShowsError başladı")

        try {
            onView(withId(R.id.etEmail)).perform(typeText("wrongEmail"), closeSoftKeyboard())
            Log.i(TAG, "Geçersiz email girildi")

            onView(withId(R.id.btnLogin)).perform(click())
            Log.i(TAG, "Login butonuna basıldı")

            onView(withText("Invalid email format")).check(matches(isDisplayed()))
            Log.i(TAG, "Geçersiz email hatası görüntülendi")

            Log.i(TAG, "testInvalidEmailShowsError başarılı")

        } catch (e: Exception) {
            Log.e(TAG, "testInvalidEmailShowsError hata: ${e.message}")
            throw e
        }
    }

    // Boş şifre hatası
    @Test
    fun testEmptyPasswordShowsError() {
        Log.i(TAG, "testEmptyPasswordShowsError başladı")

        try {
            onView(withId(R.id.etPassword)).perform(typeText(""), closeSoftKeyboard())
            Log.i(TAG, "Boş password girildi")

            onView(withId(R.id.btnLogin)).perform(click())
            Log.i(TAG, "Login butonuna basıldı")

            onView(withText("Password cannot be empty")).check(matches(isDisplayed()))
            Log.i(TAG, "Boş şifre hatası görüntülendi")

            Log.i(TAG, "testEmptyPasswordShowsError başarılı")

        } catch (e: Exception) {
            Log.e(TAG, "testEmptyPasswordShowsError hata: ${e.message}")
            throw e
        }
    }
}
