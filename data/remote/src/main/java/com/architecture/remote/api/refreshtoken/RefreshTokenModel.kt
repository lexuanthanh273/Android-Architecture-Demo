package com.architecture.remote.api.refreshtoken

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshTokenModel (
    @Json(name = "access_token")
    var accessToken: String,
    @Json(name = "refresh_token")
    var refreshToken: String

)