package com.architecture.home

import android.os.Bundle
import androidx.activity.viewModels
import com.architecture.core.base.BaseActivity
import com.architecture.core.base.BaseSharedViewModel
import com.architecture.core.ext.viewBinding
import com.architecture.home.databinding.ActivityHomeBinding
import com.architecture.preference.AppPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val viewModel: HomeSharedViewModel by viewModels()

    private val binding by viewBinding(ActivityHomeBinding::inflate)

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun getViewModel(): BaseSharedViewModel = viewModel

    override fun getMinBackstackCount() = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        handleObservable()
    }
    private fun initView() {

    }

    private fun handleObservable() {
    }

}