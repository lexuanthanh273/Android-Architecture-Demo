package com.architecture.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.architecture.core.base.BaseFragment
import com.architecture.core.base.collectFlowOn
import com.architecture.core.ext.*
import com.architecture.core.manager.NetworkConnectionManager
import com.architecture.home.databinding.FragmentHomePagerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomePagerFragment : BaseFragment(R.layout.fragment_home_pager) {

    @Inject
    lateinit var networkConnectionManager: NetworkConnectionManager

    private val viewModel: HomeSharedViewModel by activityViewModels()

    private val binding by viewBinding(FragmentHomePagerBinding::bind)

    private var homeAdapter by autoCleared<HomeActivityAdapter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        handleObservable()
    }


    private fun initView() {
    }

    private fun handleObservable() {
        collectFlowOn(networkConnectionManager.isNetworkConnectedFlow) { isConnected ->
            if (isConnected) {
                binding.tvWaitingInternet.gone()
            } else {
                launch {
                    delay(500)
                    if (!networkConnectionManager.isNetworkConnected) {
                        binding.tvWaitingInternet.visible()
                    }
                }
            }
        }

    }

}