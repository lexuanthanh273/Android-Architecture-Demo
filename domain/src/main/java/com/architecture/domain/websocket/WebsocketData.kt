package com.architecture.domain.websocket

import com.architecture.domain.friend.Friend

data class WebsocketData(
    val command: WebSocketCommand,
    val channel: WebSocketChannel,
    val friend: Friend? = null,
)