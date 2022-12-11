package com.architecture.core.bindingadapter

//import android.view.View
//import android.view.ViewGroup
//import androidx.core.view.*
//import androidx.databinding.BindingAdapter
//
//@BindingAdapter("app:animationWithKeyboard")
//fun animationWithKeyboard(view: View, isEnable: Boolean) {
//    val inputLayoutMarginBottom = view.marginBottom
//    val callback = object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {
//        override fun onProgress(
//            insets: WindowInsetsCompat,
//            runningAnimations: MutableList<WindowInsetsAnimationCompat>
//        ): WindowInsetsCompat {
//            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
//                val imeBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
//                val navigationBarBottom =
//                    insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
//                updateMargins(
//                    bottom = inputLayoutMarginBottom
//                            + imeBottom
//                            + navigationBarBottom
//                )
//            }
//            return insets
//        }
//    }
//    ViewCompat.setWindowInsetsAnimationCallback(view, callback)
//}


//https://medium.com/androiddevelopers/windowinsets-listeners-to-layouts-8f9ccc8fa4d1
//@BindingAdapter(
//    "insetLeftSystemWindowInsets",
//    "insetTopSystemWindowInsets",
//    "insetRightSystemWindowInsets",
//    "insetBottomSystemWindowInsets",
//    requireAll = false
//)
//fun applySystemWindows(
//    view: ToolbarLayout,
//    applyLeft: Boolean,
//    applyTop: Boolean,
//    applyRight: Boolean,
//    applyBottom: Boolean
//) {
//    view.doOnApplyWindowInsets { _, insets, padding ->
//        val left = if (applyLeft) insets.left else 0
//        val top = if (applyTop) insets.top else 0
//        val right = if (applyRight) insets.right else 0
//        val bottom = if (applyBottom) insets.bottom else 0
//
//        view.layoutParent.updatePadding(
//            padding.left + left,
//            padding.top + top,
//            padding.right + right,
//            padding.bottom + bottom
//        )
//    }
//}

//@BindingAdapter("app:insetTopSystemWindowInsets")
//fun insetTopStatusBar(view: View, isEnable: Boolean) {
//    ViewCompat.setOnApplyWindowInsetsListener(view) { _, windowInsets ->
//        windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
//        val insets = windowInsets.getInsets(
//            WindowInsetsCompat.Type.ime() or
//                    WindowInsetsCompat.Type.systemGestures()
//        )
//        view.updatePadding(top = insets.top)
//        windowInsets
//    }
//}