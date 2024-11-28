package com.project.kasirku.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension untuk DataStore
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStorage(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        private val LOGIN_STATUS = booleanPreferencesKey("login_status")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_ROLE = stringPreferencesKey("user_role")
        private val USERNAME_KEY = stringPreferencesKey("username_key")
        private val EMAIL_KEY = stringPreferencesKey("email_key")
    }

    suspend fun setLoginStatus(isLoggedIn: Boolean, email: String, role: String) {
        dataStore.edit { preferences ->
            preferences[LOGIN_STATUS] = isLoggedIn
            preferences[USER_EMAIL] = email
            preferences[USER_ROLE] = role
        }
    }

    val loginStatus: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[LOGIN_STATUS] ?: false
    }

    val userEmail: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USER_EMAIL]
    }

    val userRole: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USER_ROLE]
    }

    suspend fun setUserProfile(username: String, email: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
            preferences[EMAIL_KEY] = email
        }
    }

    val username: Flow<String> = dataStore.data.map { preferences ->
        preferences[USERNAME_KEY] ?: ""
    }

    val email: Flow<String> = dataStore.data.map { preferences ->
        preferences[EMAIL_KEY] ?: ""
    }


    suspend fun clearLoginStatus() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}