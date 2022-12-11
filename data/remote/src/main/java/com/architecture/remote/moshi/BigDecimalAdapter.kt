package com.architecture.remote.moshi

import android.icu.math.BigDecimal
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

object BigDecimalAdapter {
    @FromJson
    fun fromJson(value: String) = BigDecimal(value)

    @ToJson
    fun toJson(value: BigDecimal) = value.toDouble()
}