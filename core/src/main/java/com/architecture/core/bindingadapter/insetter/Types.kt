package com.architecture.core.bindingadapter.insetter

import androidx.core.view.WindowInsetsCompat

/**
 * Convenience function for building a combination of [WindowInsetsCompat.Type] values.
 */
fun windowInsetTypesOf(
    ime: Boolean = false,
    navigationBars: Boolean = false,
    statusBars: Boolean = false,
    systemGestures: Boolean = false,
    mandatorySystemGestures: Boolean = false,
    displayCutout: Boolean = false,
    captionBar: Boolean = false,
    tappableElement: Boolean = false,
): Int {
    var flag = 0
    if (ime) flag = flag or WindowInsetsCompat.Type.ime()
    if (navigationBars) flag = flag or WindowInsetsCompat.Type.navigationBars()
    if (statusBars) flag = flag or WindowInsetsCompat.Type.statusBars()
    if (systemGestures) flag = flag or WindowInsetsCompat.Type.systemGestures()
    if (displayCutout) flag = flag or WindowInsetsCompat.Type.displayCutout()
    if (captionBar) flag = flag or WindowInsetsCompat.Type.captionBar()
    if (tappableElement) flag = flag or WindowInsetsCompat.Type.tappableElement()
    if (mandatorySystemGestures) flag = flag or WindowInsetsCompat.Type.mandatorySystemGestures()
    return flag
}
