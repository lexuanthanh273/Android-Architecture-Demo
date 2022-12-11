package com.architecture.preference

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "slsettings")


@Singleton
class UserDataStorePreferences @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) {

    companion object {
        val KEY_ACCESS_TOKEN = stringPreferencesKey("key_access_token")
        val KEY_REFRESH_TOKEN = stringPreferencesKey("key_refresh_token")
        val KEY_UUID = stringPreferencesKey("key_uuid")
        val KEY_SERVER_DEVICE_ID = stringPreferencesKey("key_server_device_id")
    }

    private val dataStore = applicationContext.dataStore

    val accessToken: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[KEY_ACCESS_TOKEN] ?: ""
        }

    val refreshToken: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[KEY_REFRESH_TOKEN] ?: ""
        }

    val uuid: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[KEY_UUID] ?: ""
        }

    val serverDeviceId: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[KEY_SERVER_DEVICE_ID] ?: ""
        }

    @SuppressLint("HardwareIds")
    suspend fun initUuidIfNeed() {
        var deviceId = uuid.firstOrNull()
        // check deviceId
        if (deviceId.isNullOrEmpty()) {
            deviceId = Settings.Secure.getString(
                applicationContext.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            if (deviceId.isNullOrEmpty()) {
                deviceId = UUID.randomUUID().toString()
            }
            if (deviceId.isEmpty()) {
                deviceId = System.nanoTime().toString() + "" + Random().nextInt(1000000) + "" +
                        Random().nextInt(1000000)
            }
        }
        dataStore.edit { preferences ->
            preferences[KEY_UUID] = deviceId
        }
    }

    suspend fun saveToken(accessToken: String, refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[KEY_ACCESS_TOKEN] = accessToken
            preferences[KEY_REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun saveServerDeviceId(serverDeviceId: String) {
        dataStore.edit { preferences ->
            preferences[KEY_SERVER_DEVICE_ID] = serverDeviceId
        }
    }
}