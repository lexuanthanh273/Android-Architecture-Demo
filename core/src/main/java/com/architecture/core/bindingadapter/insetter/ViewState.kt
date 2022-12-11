package com.architecture.core.bindingadapter.insetter

import android.view.View
import android.view.ViewGroup.MarginLayoutParams

/**
 * A class which models and stores the state of some of a view's layout.
 * It has two properties: [paddings] stores the view's padding, and [margins]
 * stores the view's margins.
 */
data class ViewState(
    /**
     * Represents a view's padding values.
     */
    val paddings: ViewDimensions = ViewDimensions.EMPTY,
    /**
     * Represents a view's margins.
     */
    val margins: ViewDimensions = ViewDimensions.EMPTY
) {
    constructor(view: View) : this(
        paddings = view.paddingDimensions,
        margins = view.marginDimensions
    )
}

private val View.paddingDimensions: ViewDimensions
    get() = ViewDimensions(paddingLeft, paddingTop, paddingRight, paddingBottom)

private val View.marginDimensions: ViewDimensions
    get() {
        val lp = layoutParams
        if (lp is MarginLayoutParams) {
            return ViewDimensions(lp.leftMargin, lp.topMargin, lp.rightMargin, lp.bottomMargin)
        }
        return ViewDimensions.EMPTY
    }
