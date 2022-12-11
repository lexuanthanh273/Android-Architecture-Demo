package com.architecture.core.bindingadapter.insetter

import android.view.View
import androidx.core.view.WindowInsetsCompat

/**
 * Listener for applying window insets on a view in a custom way.
 *
 * This class is similar to to [androidx.core.view.OnApplyWindowInsetsListener] but
 * contains a third parameter on [onApplyInsets]. It is
 * designed to be used in conjunction with [Insetter.Builder.setOnApplyInsetsListener].
 */
fun interface OnApplyInsetsListener {
    /**
     * When [set][Insetter.Builder.setOnApplyInsetsListener] on a View,
     * this listener method will be called instead of the view's own `onApplyWindowInsets`
     * method.
     *
     * @param view The view applying window insets
     * @param insets The insets to apply
     * @param initialState A snapshot of the view's padding/margin state when this listener was set.
     */
    fun onApplyInsets(
        view: View,
        insets: WindowInsetsCompat,
        initialState: ViewState
    )
}
