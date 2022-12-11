package com.architecture.core.bindingadapter.insetter

import android.view.ViewGroup
import androidx.annotation.Px

internal fun ViewGroup.MarginLayoutParams.updateMargins(
    @Px left: Int = leftMargin,
    @Px top: Int = topMargin,
    @Px right: Int = rightMargin,
    @Px bottom: Int = bottomMargin
): Boolean {
    if (left != leftMargin || top != topMargin || right != rightMargin || bottom != bottomMargin) {
        setMargins(left, top, right, bottom)
        return true
    }
    return false
}
