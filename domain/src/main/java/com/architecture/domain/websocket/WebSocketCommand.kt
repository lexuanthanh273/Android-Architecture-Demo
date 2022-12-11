package com.architecture.domain.websocket

sealed class WebSocketCommand  {
    object AddCommand: WebSocketCommand()

    object UpdateCommand: WebSocketCommand()

    object DeleteCommand: WebSocketCommand()

    companion object {
        fun getWebSocketCommandBy(command: String): WebSocketCommand {
            return when(command.lowercase()) {
                "add" -> AddCommand
                "update" -> UpdateCommand
                else -> DeleteCommand
            }
        }
    }
}