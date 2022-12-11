package com.architecture.android

import android.app.Application
import com.architecture.preference.UserDataStorePreferences
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    @Inject
    lateinit var userDataStorePreferences: UserDataStorePreferences

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            userDataStorePreferences.initUuidIfNeed()
        }
    }

}