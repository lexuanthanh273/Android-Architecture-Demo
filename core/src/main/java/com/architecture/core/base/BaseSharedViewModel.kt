package com.architecture.core.base

import androidx.lifecycle.viewModelScope
import com.architecture.core.customview.loading.LoadingData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseSharedViewModel : BaseViewModel() {

    private val _showLoading = MutableStateFlow(LoadingData(false))
    val showLoading: StateFlow<LoadingData> = _showLoading
    var isShowLoading = false

    fun showLoading(message: String = "") {
        isShowLoading = true
        viewModelScope.launch {
            _showLoading.value = LoadingData(true, message)
        }
    }

    fun hideLoading() {
        isShowLoading = false
        viewModelScope.launch {
            _showLoading.value = LoadingData(false)
        }
    }
}