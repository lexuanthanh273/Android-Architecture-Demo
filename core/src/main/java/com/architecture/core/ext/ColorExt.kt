package com.architecture.core.ext

import android.graphics.Color

fun String.hextoRGB() : Triple<String, String, String>{
    var name = this
    if (!name.startsWith("#")){
        name = "#$this"
    }
    var color = Color.parseColor(name)
    var red = Color.red(color)
    var green = Color.green(color)
    var blue = Color.blue(color)

    return Triple(red.toString(), green.toString(), blue.toString())
}

fun Int.colorToHexString(): String? {
    var data = String.format("#%06X", -0x1 and this).replace("#FF", "#")
    return data
}