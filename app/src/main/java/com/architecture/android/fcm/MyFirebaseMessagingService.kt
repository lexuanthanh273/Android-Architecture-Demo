package com.architecture.android.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.architecture.domain.pushtoken.PushTokenParam
import com.architecture.domain.pushtoken.PushTokenRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService: FirebaseMessagingService() {

    companion object {
        private val TAG = MyFirebaseMessagingService::class.java.simpleName
    }

    @Inject lateinit var pushTokenRepository: PushTokenRepository

    private val job = SupervisorJob()

    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    private fun sendRegistrationToServer(token: String) {
        coroutineScope.launch {
            pushTokenRepository.pushToken(PushTokenParam(token))
        }
    }
}
