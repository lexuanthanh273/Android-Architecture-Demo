package com.architecture.core.pushdown

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.View.OnLongClickListener
import android.view.View.OnTouchListener
import java.util.ArrayList

class PushDownAnimList internal constructor(vararg views: View) : PushDown {
    private val pushDownList: MutableList<PushDownAnim> = ArrayList()
    override fun setScale(scale: Float): PushDownAnimList {
        for (pushDown in pushDownList) {
            pushDown.setScale(scale)
        }
        return this
    }

    override fun setScale(mode: Int, scale: Float): PushDown {
        for (pushDown in pushDownList) {
            pushDown.setScale(mode, scale)
        }
        return this
    }

    override fun setDurationPush(duration: Long): PushDownAnimList {
        for (pushDown in pushDownList) {
            pushDown.setDurationPush(duration)
        }
        return this
    }

    override fun setDurationRelease(duration: Long): PushDownAnimList {
        for (pushDown in pushDownList) {
            pushDown.setDurationRelease(duration)
        }
        return this
    }

    override fun setInterpolatorPush(interpolatorPush: AccelerateDecelerateInterpolator): PushDownAnimList {
        for (pushDown in pushDownList) {
            pushDown.setInterpolatorPush(interpolatorPush)
        }
        return this
    }

    override fun setInterpolatorRelease(interpolatorRelease: AccelerateDecelerateInterpolator): PushDownAnimList {
        for (pushDown in pushDownList) {
            pushDown.setInterpolatorRelease(interpolatorRelease)
        }
        return this
    }

    override fun setOnClickListener(clickListener: View.OnClickListener): PushDownAnimList {
        for (pushDown in pushDownList) {
            pushDown.setOnClickListener(clickListener)
        }
        return this
    }

    override fun setOnLongClickListener(clickListener: OnLongClickListener): PushDown {
        for (pushDown in pushDownList) {
            pushDown.setOnLongClickListener(clickListener)
        }
        return this
    }

    override fun setOnTouchEvent(eventListener: OnTouchListener?): PushDownAnimList {
        for (pushDown in pushDownList) {
            if (eventListener != null) {
                pushDown.setOnTouchEvent(eventListener)
            }
        }
        return this
    }

    init {
        for (view in views) {
            val pushDown: PushDownAnim = PushDownAnim.setPushDownAnimTo(view)
            pushDown.setOnTouchEvent(null)
            pushDownList.add(pushDown)
        }
    }
}