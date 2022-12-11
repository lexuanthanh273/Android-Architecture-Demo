package com.architecture.remote.websocket.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WebsocketModel(
    @Json(name = "command")
    val command: String,

    @Json(name = "channel")
    val channel: String,

    @Json(name = "data")
    val data: String
)