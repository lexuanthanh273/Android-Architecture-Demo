package com.architecture.core.bindingadapter.insetter

import androidx.annotation.Px

/**
 * A data class which represents a pixel length on each dimension of a view.
 */
data class ViewDimensions(
    @Px val left: Int,
    @Px val top: Int,
    @Px val right: Int,
    @Px val bottom: Int
) {
    companion object {
        /**
         * An instance of [ViewDimensions] which is empty.
         */
        @JvmField
        val EMPTY = ViewDimensions(0, 0, 0, 0)
    }
}
