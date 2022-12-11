package com.architecture.core.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class BaseDialog(context: Context) : Dialog(context), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    companion object {
        const val TABLET_RATIO_WIDTH_DIALOG = 0.85f
        const val PHONE_RATIO_WIDTH_DIALOG = 0.8f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView()
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setCancelable(isCancelable)
        initView()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        coroutineContext.cancelChildren()
    }

    override fun dismiss() {
        super.dismiss()
        coroutineContext.cancelChildren()
    }

    protected abstract val isCancelable: Boolean

    protected abstract fun setContentView()
    protected abstract fun initView()

}
