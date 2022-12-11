@file:JvmName("OkHttpClientUtils")

package com.architecture.remote.websocket.okhttp

import com.tinder.scarlet.WebSocket
import com.architecture.remote.websocket.okhttp.request.RequestFactory
import com.architecture.remote.websocket.okhttp.request.StaticUrlRequestFactory
import okhttp3.OkHttpClient

fun OkHttpClient.newWebSocketFactory(requestFactory: RequestFactory): WebSocket.Factory {
    return OkHttpWebSocket.Factory(OkHttpClientWebSocketConnectionEstablisher(this, requestFactory))
}

fun OkHttpClient.newWebSocketFactory(url: String): WebSocket.Factory {
    return newWebSocketFactory(StaticUrlRequestFactory(url))
}
