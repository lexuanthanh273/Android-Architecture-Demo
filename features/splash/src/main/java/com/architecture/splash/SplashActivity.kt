package com.architecture.splash

import android.os.Bundle
import androidx.activity.viewModels
import com.architecture.core.base.BaseActivity
import com.architecture.core.base.BaseSharedViewModel
import com.architecture.core.bindingadapter.insetter.Insetter
import com.architecture.core.bindingadapter.insetter.windowInsetTypesOf
import com.architecture.core.ext.viewBinding
import com.architecture.splash.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    val viewModel: SplashSharedViewModel by viewModels()

    private val binding by viewBinding(ActivitySplashBinding::inflate)

    override fun getViewModel(): BaseSharedViewModel = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        Insetter.builder()
            .marginBottom(windowInsetTypesOf(navigationBars = true))
            .paddingTop(windowInsetTypesOf(statusBars = true))
            .applyToView(binding.fragmentContainer)
    }
}