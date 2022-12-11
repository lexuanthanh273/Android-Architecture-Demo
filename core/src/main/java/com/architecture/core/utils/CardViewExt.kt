package com.architecture.core.utils

import android.content.res.ColorStateList
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.CornerSize
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.architecture.core.R

fun CardView.roundBottom() {
    val leftShapePathModel = ShapeAppearanceModel().toBuilder()
    // You can change style and size
    leftShapePathModel.setBottomLeftCorner(
        CornerFamily.ROUNDED,
        CornerSize { return@CornerSize resources.getDimension(R.dimen.corner_base) })

    leftShapePathModel.setBottomRightCorner(
        CornerFamily.ROUNDED,
        CornerSize { return@CornerSize resources.getDimension(R.dimen.corner_base) })

    val bg = MaterialShapeDrawable(leftShapePathModel.build())
    // In my screen without applying color it shows black background
    bg.fillColor = ColorStateList.valueOf(
        ContextCompat.getColor(context, R.color.white)
    )

    background = bg
    invalidate()
}

fun CardView.roundTop() {
    val leftShapePathModel = ShapeAppearanceModel().toBuilder()
    // You can change style and size
    leftShapePathModel.setTopLeftCorner(
        CornerFamily.ROUNDED,
        CornerSize { return@CornerSize resources.getDimension(R.dimen.corner_base) })

    leftShapePathModel.setTopRightCorner(
        CornerFamily.ROUNDED,
        CornerSize { return@CornerSize resources.getDimension(R.dimen.corner_base) })

    val bg = MaterialShapeDrawable(leftShapePathModel.build())
    // In my screen without applying color it shows black background
    bg.fillColor = ColorStateList.valueOf(
        ContextCompat.getColor(context, R.color.white)
    )

    background = bg
    invalidate()
}

