@file:Suppress("unused", "UNUSED_PARAMETER", "NOTHING_TO_INLINE")

package com.architecture.core.bindingadapter.insetter

import android.view.View

@Deprecated(
    level = DeprecationLevel.ERROR,
    message = "Use applyInsetter instead",
    replaceWith = ReplaceWith(
        "applyInsetter { type(statusBars = top, navigationBars = bottom) { padding() } }",
        "com.smartlottery.core.bindingadapter.insetter.applyInsetter"
    )
)
inline fun View.applySystemWindowInsetsToPadding(top: Boolean = false, bottom: Boolean = false) {
    noImpl()
}

@Deprecated(
    level = DeprecationLevel.ERROR,
    message = "Use applyInsetter instead",
    replaceWith = ReplaceWith(
        "applyInsetter { type(statusBars = top, navigationBars = bottom) { padding() }; consume(consume) }",
        "com.smartlottery.core.bindingadapter.insetter.applyInsetter"
    )
)
inline fun View.applySystemWindowInsetsToPadding(top: Boolean = false, bottom: Boolean = false, consume: Boolean) {
    noImpl()
}

@Deprecated(
    level = DeprecationLevel.ERROR,
    message = "Use applyInsetter instead",
    replaceWith = ReplaceWith(
        "applyInsetter { type(statusBars = top, navigationBars = bottom) { margin() } }",
        "com.smartlottery.core.bindingadapter.insetter.applyInsetter"
    )
)
inline fun View.applySystemWindowInsetsToMargin(top: Boolean = false, bottom: Boolean = false) {
    noImpl()
}

@Deprecated(
    level = DeprecationLevel.ERROR,
    message = "Use applyInsetter instead",
    replaceWith = ReplaceWith(
        "applyInsetter { type(statusBars = top, navigationBars = bottom) { margin() }; consume(consume) }",
        "com.smartlottery.core.bindingadapter.insetter.applyInsetter"
    )
)
inline fun View.applySystemWindowInsetsToMargin(top: Boolean = false, bottom: Boolean = false, consume: Boolean) {
    noImpl()
}

@PublishedApi
internal inline fun noImpl(): Nothing =
    throw UnsupportedOperationException("Not implemented, should not be called")
