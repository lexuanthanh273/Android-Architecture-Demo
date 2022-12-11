package com.architecture.core.ext

import android.content.Context
import android.os.SystemClock
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar

/**
 * View's visibility extensions.
 */

fun View.visible() {
    changeViewVisibility(View.VISIBLE)
}

fun View.gone() {
    changeViewVisibility(View.GONE)
}

fun View.invisible() {
    changeViewVisibility(View.INVISIBLE)
}

private fun View.changeViewVisibility(newState: Int) {
    visibility = newState
}

/**
 * Snackbar's extensions.
 */

fun View.showLongSnackbar(text: String) {
    showSnackbar(text, Snackbar.LENGTH_LONG)
}

// Snackbar Extensions
fun View.showShotSnackbar(message : String){
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun View.snackBarWithAction(message : String, actionlable : String,
                            block : () -> Unit){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .setAction(actionlable) {
            block()
        }
}

fun View.showIndefiniteSnackbar(text: String) {
    showSnackbar(text, Snackbar.LENGTH_INDEFINITE)
}

private fun View.showSnackbar(text: String, duration: Int) {
    Snackbar.make(this, text, duration).show()
}



fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) { }
    return false
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

private var lastClickTime = 0L
private const val THRESHOLD_DOUBLE_TIME = 500

fun View.setOnClickPreventingDouble(onClick: () -> Unit) {
    setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickTime < THRESHOLD_DOUBLE_TIME) {
            return@setOnClickListener
        }
        onClick.invoke()
        lastClickTime = SystemClock.elapsedRealtime()
    }
}


fun View.margin(left: Float? = null, top: Float? = null, right: Float? = null, bottom: Float? = null) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = dpToPx(this) }
        top?.run { topMargin = dpToPx(this) }
        right?.run { rightMargin = dpToPx(this) }
        bottom?.run { bottomMargin = dpToPx(this) }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()