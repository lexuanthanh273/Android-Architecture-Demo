package com.architecture.core.manager

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConnectionManager @Inject constructor(
    @ApplicationContext context: Context,
    coroutineScope: CoroutineScope
)  {

    private val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager

    private val networkCallback = NetworkCallback()

    private val _currentNetwork = MutableStateFlow(provideDefaultCurrentNetwork())

    val isNetworkConnectedFlow: StateFlow<Boolean> =
        _currentNetwork
            .map { it.isConnected() }
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = _currentNetwork.value.isConnected()
            )

    val isNetworkConnected: Boolean
        get() = isNetworkConnectedFlow.value

    init {
        startListenNetworkState()
    }

    private fun startListenNetworkState() {
        if (_currentNetwork.value.isListening) {
            return
        }

        // Reset state before start listening
        _currentNetwork.update {
            provideDefaultCurrentNetwork()
                .copy(isListening = true)
        }
        connectivityManager.registerDefaultNetworkCallback(networkCallback)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            connectivityManager.registerBestMatchingNetworkCallback(NetworkRequest.Builder().build())
//        } else {
//        }
    }

    fun stopListenNetworkState() {
        if (!_currentNetwork.value.isListening) {
            return
        }

        _currentNetwork.update {
            it.copy(isListening = false)
        }

        connectivityManager.unregisterNetworkCallback(networkCallback)
    }


    private inner class NetworkCallback : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _currentNetwork.update {
                it.copy(isAvailable = true)
            }
        }

        override fun onLost(network: Network) {
            _currentNetwork.update {
                it.copy(
                    isAvailable = false,
                    networkCapabilities = null
                )
            }
        }

        override fun onUnavailable() {
            _currentNetwork.update {
                it.copy(
                    isAvailable = false,
                    networkCapabilities = null
                )
            }
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            _currentNetwork.update {
                it.copy(networkCapabilities = networkCapabilities)
            }
        }

        override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
            _currentNetwork.update {
                it.copy(isBlocked = blocked)
            }
        }
    }

    /**
     * On Android 9, [ConnectivityManager.NetworkCallback.onBlockedStatusChanged] is not called when
     * we call the [ConnectivityManager.registerDefaultNetworkCallback] function.
     * Hence we assume that the network is unblocked by default.
     */
    private fun provideDefaultCurrentNetwork(): CurrentNetwork {
        return CurrentNetwork(
            isListening = false,
            networkCapabilities = null,
            isAvailable = false,
            isBlocked = false
        )
    }

    private data class CurrentNetwork(
        val isListening: Boolean,
        val networkCapabilities: NetworkCapabilities?,
        val isAvailable: Boolean,
        val isBlocked: Boolean
    )

    private fun CurrentNetwork.isConnected(): Boolean {
        // Since we don't know the network state if NetworkCallback is not registered.
        // We assume that it's disconnected.
        return isListening &&
                isAvailable &&
                !isBlocked &&
                networkCapabilities.isNetworkCapabilitiesValid()
    }

    private fun NetworkCapabilities?.isNetworkCapabilitiesValid(): Boolean = when {
        this == null -> false
        hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                (hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) -> true
        else -> false
    }
}