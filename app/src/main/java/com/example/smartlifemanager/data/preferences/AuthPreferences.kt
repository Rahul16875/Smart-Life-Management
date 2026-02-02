package com.example.smartlifemanager.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

private object Keys {
    val IS_GUEST = booleanPreferencesKey("is_guest")
}

class AuthPreferences(private val context: Context) {

    val isGuest: Flow<Boolean> = context.authDataStore.data.map { prefs ->
        prefs[Keys.IS_GUEST] ?: false
    }

    suspend fun getIsGuest(): Boolean =
        context.authDataStore.data.map { prefs -> prefs[Keys.IS_GUEST] ?: false }.first()

    suspend fun setGuest(value: Boolean) {
        context.authDataStore.edit { prefs ->
            prefs[Keys.IS_GUEST] = value
        }
    }
}
