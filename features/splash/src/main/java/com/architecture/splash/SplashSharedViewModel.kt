package com.architecture.splash

import androidx.lifecycle.viewModelScope
import com.architecture.core.base.BaseSharedViewModel
import com.architecture.domain.UiResource
import com.architecture.domain.device.Device
import com.architecture.domain.device.info.GetDeviceInfoUseCase
import com.architecture.preference.UserDataStorePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashSharedViewModel @Inject constructor(
    private val getDeviceInfoUseCase: GetDeviceInfoUseCase,
    private val userDataStorePreferences: UserDataStorePreferences,
): BaseSharedViewModel() {

    private val _deviceInfo = MutableStateFlow<UiResource<Device>>(UiResource.Loading())
    val deviceInfo: StateFlow<UiResource<Device>> = _deviceInfo

    private val _isLoginFlow = MutableSharedFlow<Boolean>()
    val isLoginFlow: SharedFlow<Boolean> = _isLoginFlow

    fun checkUserLogin() {
        viewModelScope.launch(Dispatchers.Main) {
            val accessToken = userDataStorePreferences.accessToken.first()
            _isLoginFlow.emit(accessToken.isNotBlank())
        }
    }

    fun fetchDeviceInfo() {
        _deviceInfo.value = UiResource.Loading()
        viewModelScope.launch(Dispatchers.Main) {
            _deviceInfo.emit(getDeviceInfoUseCase.invoke(userDataStorePreferences.uuid.first()))
        }
    }

    fun clearLoginData() {
        viewModelScope.launch {
            userDataStorePreferences.saveServerDeviceId("")
            userDataStorePreferences.saveToken("", "")
        }
    }
}