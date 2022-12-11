package com.smartlottery.remote.interceptor

import com.smartlottery.preference.UserDataStorePreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(
    private val dataStorePreferences: UserDataStorePreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("Content-Type", "application/json")
            .header("User-Agent", "android")

        val accessToken = runBlocking {
             dataStorePreferences.accessToken.first()
        }
        if (accessToken.isNotBlank()) {
            requestBuilder.header("Authorization", "Bearer $accessToken")
        }
        requestBuilder.header("is_wiremock", "mock")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}