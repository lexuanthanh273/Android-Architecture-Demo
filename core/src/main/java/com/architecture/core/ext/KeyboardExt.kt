package com.architecture.core.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun View.showSoftKeyboard(force: Boolean = false) {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (force) {
        inputMethodManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    } else {
        inputMethodManager.toggleSoftInputFromWindow(
            applicationWindowToken,
            InputMethodManager.SHOW_FORCED, 0
        )
    }
}

fun EditText.hideKeyBoard() {
    val imm =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}
