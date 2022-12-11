package com.architecture.remote.api.refreshtoken

import com.architecture.remote.*
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {

    @POST(ApiConstants.RefreshToken.REFRESH_TOKEN)
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): GenericResponse<GenericApiResponse<RefreshTokenModel>>
}