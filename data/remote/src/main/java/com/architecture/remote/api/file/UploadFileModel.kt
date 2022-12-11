package com.architecture.remote.api.file

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UploadFileModel (
    @Json(name = "path")
    val path: String,
)