package com.architecture.core.base.dialog

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.architecture.core.R

open class BaseBottomSheetDialog(
    context: Context
) : BottomSheetDialog(
    context,
    R.style.BaseBottomSheetDialog
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.let {
            val display = it.windowManager.defaultDisplay
            val metrics = DisplayMetrics()
            display.getRealMetrics(metrics)
            val minSize = metrics.widthPixels.coerceAtMost(metrics.heightPixels)
            it.setLayout(minSize, -1)
            it.statusBarColor = Color.TRANSPARENT
        }
        setCanceledOnTouchOutside(true)
    }
}