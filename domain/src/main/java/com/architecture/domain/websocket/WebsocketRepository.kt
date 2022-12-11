package com.architecture.domain.websocket

import android.graphics.Bitmap
import com.architecture.domain.chat.ChatMessage
import io.reactivex.Flowable

interface WebsocketRepository {
    fun observeChatMessage(): Flowable<List<ChatMessage>>

    fun observeWebsocketData(): Flowable<List<WebsocketData>>

    fun addTextMessage(text: String)

    fun addImageMessage(bitmap: Bitmap)

    fun clearAllMessages()
}