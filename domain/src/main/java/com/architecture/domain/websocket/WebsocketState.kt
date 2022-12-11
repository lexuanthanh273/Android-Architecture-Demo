package com.architecture.domain.websocket

sealed class WebsocketState {

    object OnLifecycleStateChangeStart: WebsocketState()

    object OnLifecycleStateChangeStop: WebsocketState()

    object OnLifecycleStateChangeTerminate: WebsocketState()

    object OnLifecycleTerminate: WebsocketState()

    object OnWebsocketEventConnectionOpened: WebsocketState()

    object OnWebsocketEventOnMessageReceived: WebsocketState()

    object OnWebsocketEventConnectionClosing: WebsocketState()

    object OnWebsocketEventConnectionClosed: WebsocketState()

    object OnWebsocketEventConnectionFailed: WebsocketState()

    object OnWebsocketTerminate: WebsocketState()

    object OnStateChangeWaitingToRetry: WebsocketState()

    object OnStateChangeConnecting: WebsocketState()

    object OnStateChangeConnected: WebsocketState()

    object OnStateChangeDisconnecting: WebsocketState()

    object OnStateChangeDisconnected: WebsocketState()

    object OnStateChangeDestroyed: WebsocketState()

    object OnRetry: WebsocketState()

}