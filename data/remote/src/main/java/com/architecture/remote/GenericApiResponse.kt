package com.architecture.remote

import com.squareup.moshi.Json

data class GenericApiResponse<T>(
    @Json(name = "data")
    val data: T? = null,

    @Json(name = "status")
    val status: String,

    @Json(name = "message")
    val message: String?,

    @Json(name = "code")
    val code: Int,

    )