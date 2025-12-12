package com.example.healifyapp

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences // <-- Yeni import
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.catch // <-- Yeni import
import java.io.IOException // <-- Yeni import

class UserPreferences(private val context: Context) {

    companion object {
        private val TAG = "USER_PREF_LOG"

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_prefs")

        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_PASSWORD = stringPreferencesKey("user_password")
        val USER_NAME = stringPreferencesKey("user_name")
    }

    // EMAIL
    val getEmail: Flow<String> =
        context.dataStore.data
            .catch { exception -> // DataStore okuma hatası yakalanırsa (örn. IO hatası) boş tercih seti döndürülür.
                if (exception is IOException) {
                    Log.e(TAG, "Error reading preferences.", exception)
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[USER_EMAIL] ?: ""
            }

    // PASSWORD
    val getPassword: Flow<String> =
        context.dataStore.data.map { prefs ->
            prefs[USER_PASSWORD] ?: ""
        }

    // NAME
    val getName: Flow<String> =
        context.dataStore.data.map { prefs ->
            prefs[USER_NAME] ?: ""
        }

    // KAYDETME
    suspend fun saveUser(email: String, password: String, name: String) {
        try {
            context.dataStore.edit { prefs ->
                prefs[USER_EMAIL] = email
                prefs[USER_PASSWORD] = password
                prefs[USER_NAME] = name
            }

            Log.i(TAG, "saveUser: email=$email password=$password name=$name kaydedildi")

        } catch (e: Exception) {
            Log.e(TAG, "saveUser hata: ${e.message}")
        }
    }

    /** YENİ EKLENEN FONKSİYONLAR **/

    // OTURUM KONTROLÜ
    // USER_EMAIL anahtarının boş olup olmadığını kontrol ederek oturumun açık olup olmadığını belirten bir akış (Flow) döndürür.
    val isLoggedIn: Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[USER_EMAIL].isNullOrEmpty().not()
        }

    // OTURUMU KAPATMA (LOGOUT)
    // DataStore'daki tüm kullanıcı verilerini silerek kullanıcının oturumunu sonlandırır.
    suspend fun clearUser() {
        try {
            context.dataStore.edit {
                it.clear()
            }
            Log.i(TAG, "clearUser: Kullanıcı oturumu silindi.")
        } catch (e: Exception) {
            Log.e(TAG, "clearUser hata: ${e.message}")
        }
    }
}