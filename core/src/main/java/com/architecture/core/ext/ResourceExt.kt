package com.architecture.core.ext

import android.content.Context
import androidx.core.content.ContextCompat

fun Int.asColor(applicationContext: Context) = ContextCompat.getColor(applicationContext, this)
fun Int.asDrawable(applicationContext: Context) = ContextCompat.getDrawable(applicationContext, this)
