package com.architecture.core.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.architecture.core.R

class DividerColorItemDecoration constructor(
    context: Context,
    colorId: Int = R.color.background_1,
    heightDimenId: Int = R.dimen._1sdp,
) : ItemDecoration() {

    private val paint: Paint = Paint()
    private val heightDp: Int

    init {
        paint.style = Paint.Style.FILL
        paint.color = ContextCompat.getColor(context, colorId)
        heightDp = context.resources.getDimensionPixelSize(heightDimenId)
    }
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (hasDividerOnBottom(view, parent, state)) {
            outRect[0, 0, 0] = heightDp
        } else {
            outRect.setEmpty()
        }
    }

    private fun hasDividerOnBottom(
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ): Boolean {
//        val position = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
//        return position < state.itemCount && parent.adapter!!.getItemViewType(position) != HeaderItemTestAdapter.HEADER && parent.adapter!!.getItemViewType(
//            position + 1
//        ) != HeaderItemTestAdapter.HEADER
        return true
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            if (hasDividerOnBottom(view, parent, state)) {
                c.drawRect(
                    view.left.toFloat(),
                    view.bottom.toFloat(),
                    view.right.toFloat(),
                    (view.bottom + heightDp).toFloat(),
                    paint
                )
            }
        }
    }

}