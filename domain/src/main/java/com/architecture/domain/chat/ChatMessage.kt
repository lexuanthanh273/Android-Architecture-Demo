package com.architecture.domain.chat

import android.graphics.Bitmap
import com.architecture.domain.websocket.WebsocketState
import java.util.*

sealed class ChatMessage {
    abstract val id: Int
    abstract val source: Source
    abstract val timestamp: Long

    data class Text(
        override val id: Int,
        val value: String,
        override val source: Source,
        override val timestamp: Long = Date().time
    ) : ChatMessage()

    data class Image(
        override val id: Int,
        val bitmap: Bitmap,
        override val source: Source,
        override val timestamp: Long = Date().time
    ) : ChatMessage()

    data class State(
        override val id: Int,
        val websocketState: WebsocketState,
        override val source: Source,
        override val timestamp: Long = Date().time
    ) : ChatMessage()

    enum class Source {
        SENT, RECEIVED
    }


}
