package com.architecture.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.architecture.core.base.BaseFragment
import com.architecture.core.ext.viewBinding
import com.architecture.navigation.NavigationManager
import com.architecture.navigation.ScreenType
import com.architecture.splash.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val viewModel: SplashSharedViewModel by activityViewModels()

    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateViews()
        handleObservable()
        viewModel.checkUserLogin()
    }

    private fun initiateViews() {

    }

    private fun handleObservable() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.isLoginFlow.collectLatest { isLogin ->
                    if (isLogin) {
                        NavigationManager.navigateToHomeActivity(requireContext())
                    } else {
                        NavigationManager.navigateToAuthActivity(
                            requireContext(),
                            ScreenType.LoginFlow.Login
                        )
                    }
                }
            }
        }
    }
}