package com.architecture.core.recyclerview

import androidx.recyclerview.widget.RecyclerView
import android.graphics.Rect
import android.view.View

class LinearSpacingItemDecoration(
    private val verticalSpacing: Int,
    private val horizontalSpacing: Int = 0,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = horizontalSpacing
        outRect.right = horizontalSpacing
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = verticalSpacing
        }
        outRect.bottom = verticalSpacing
    }
}