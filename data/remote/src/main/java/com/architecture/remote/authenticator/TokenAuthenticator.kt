package com.architecture.remote.authenticator

import com.architecture.preference.UserDataStorePreferences
import com.architecture.remote.GenericApiError
import com.architecture.remote.GenericApiResponse
import com.architecture.remote.NetworkResponse
import com.architecture.remote.api.refreshtoken.RefreshTokenRequest
import com.architecture.remote.api.refreshtoken.RefreshTokenService
import com.architecture.remote.api.refreshtoken.RefreshTokenModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val refreshTokenService: RefreshTokenService,
    private val dataStorePreferences: UserDataStorePreferences
): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val refreshToken = dataStorePreferences.refreshToken.first()
            when(val tokenResponse = getRefreshTokenModel(refreshToken)) {
                is NetworkResponse.Success -> {
                    val refreshTokenModel = tokenResponse.body.data!!
                    dataStorePreferences.saveToken(refreshTokenModel.accessToken, refreshTokenModel.refreshToken)
                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${refreshTokenModel.accessToken}")
                        .build()
                }
                else -> null
            }
        }
    }

    private suspend fun getRefreshTokenModel(
        refreshToken: String
    ): NetworkResponse<GenericApiResponse<RefreshTokenModel>, GenericApiError> {
        return refreshTokenService.refreshToken(RefreshTokenRequest(refreshToken))
    }
}