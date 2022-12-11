package com.architecture.domain.websocket

sealed class WebSocketChannel  {

    object FriendChannel: WebSocketChannel()

    companion object {
        fun getWebSocketChannelBy(command: String): WebSocketChannel {
            return when(command.lowercase()) {
                "friend" -> FriendChannel
                else -> FriendChannel
            }
        }
    }
}