package com.architecture.remote.api.friend

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FriendModel(
    @Json(name = "id")
    var id: String,

    @Json(name = "user_id")
    var userId: String,

    @Json(name = "fullname")
    var fullname: String,

    @Json(name = "avatar")
    var avatar: String,

    @Json(name = "phone")
    var phone: String,

)

