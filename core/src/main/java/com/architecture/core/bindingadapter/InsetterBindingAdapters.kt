package com.architecture.core.bindingadapter

//import android.view.View
//import androidx.databinding.BindingAdapter
//import com.smartlottery.core.bindingadapter.insetter.Insetter
//import com.smartlottery.core.bindingadapter.insetter.sidesOf
//import com.smartlottery.core.bindingadapter.insetter.windowInsetTypesOf
//
//@BindingAdapter(
//    value = [
//        "consumeWindowInsets",
//        "paddingLeftSystemWindowInsets",
//        "paddingTopSystemWindowInsets",
//        "paddingRightSystemWindowInsets",
//        "paddingBottomSystemWindowInsets",
//        "paddingLeftSystemGestureInsets",
//        "paddingTopSystemGestureInsets",
//        "paddingRightSystemGestureInsets",
//        "paddingBottomSystemGestureInsets",
//        "layout_marginLeftSystemWindowInsets",
//        "layout_marginTopSystemWindowInsets",
//        "layout_marginRightSystemWindowInsets",
//        "layout_marginBottomSystemWindowInsets",
//        "layout_marginLeftSystemGestureInsets",
//        "layout_marginTopSystemGestureInsets",
//        "layout_marginRightSystemGestureInsets",
//        "layout_marginBottomSystemGestureInsets"
//    ],
//    requireAll = false
//)
//fun applyInsetsFromBooleans(
//    v: View,
//    consumeWindowInsets: Boolean,
//    padSystemWindowLeft: Boolean,
//    padSystemWindowTop: Boolean,
//    padSystemWindowRight: Boolean,
//    padSystemWindowBottom: Boolean,
//    padSystemGestureLeft: Boolean,
//    padSystemGestureTop: Boolean,
//    padSystemGestureRight: Boolean,
//    padSystemGestureBottom: Boolean,
//    marginSystemWindowLeft: Boolean,
//    marginSystemWindowTop: Boolean,
//    marginSystemWindowRight: Boolean,
//    marginSystemWindowBottom: Boolean,
//    marginSystemGestureLeft: Boolean,
//    marginSystemGestureTop: Boolean,
//    marginSystemGestureRight: Boolean,
//    marginSystemGestureBottom: Boolean
//) {
//    Insetter.builder()
//        .padding(
//            windowInsetTypesOf(ime = true, statusBars = true, navigationBars = true),
//            sidesOf(
//                padSystemWindowLeft,
//                padSystemWindowTop,
//                padSystemWindowRight,
//                padSystemWindowBottom
//            )
//        )
//        .margin(
//            windowInsetTypesOf(ime = true, statusBars = true, navigationBars = true),
//            sidesOf(
//                marginSystemWindowLeft,
//                marginSystemWindowTop,
//                marginSystemWindowRight,
//                marginSystemWindowBottom
//            )
//        )
//        .padding(
//            windowInsetTypesOf(systemGestures = true),
//            sidesOf(
//                padSystemGestureLeft,
//                padSystemGestureTop,
//                padSystemGestureRight,
//                padSystemGestureBottom
//            )
//        )
//        .margin(
//            windowInsetTypesOf(systemGestures = true),
//            sidesOf(
//                marginSystemGestureLeft,
//                marginSystemGestureTop,
//                marginSystemGestureRight,
//                marginSystemGestureBottom
//            )
//        )
//        .consume(if (consumeWindowInsets) Insetter.CONSUME_ALL else Insetter.CONSUME_NONE)
//        .applyToView(v)
//}
