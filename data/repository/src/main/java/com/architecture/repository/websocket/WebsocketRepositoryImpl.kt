package com.architecture.repository.websocket

import android.graphics.Bitmap
import com.architecture.domain.chat.ChatMessage
import com.architecture.domain.websocket.*
import com.architecture.remote.api.friend.FriendModel
import com.architecture.remote.websocket.model.WebsocketModel
import com.architecture.remote.websocket.service.echo.EchoService
import com.architecture.repository.data.friend.FriendModelMapper
import com.squareup.moshi.Moshi
import com.tinder.scarlet.*
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class WebsocketRepositoryImpl @Inject constructor(
    private val echoService: EchoService,
    coroutineScope: CoroutineScope,
    private val moshi: Moshi,
    private val friendModelMapper: FriendModelMapper,
): WebsocketRepository {
    private val messageCount = AtomicInteger()
    private val messagesRef = AtomicReference<List<ChatMessage>>()
    private val messagesProcessor = BehaviorProcessor.create<List<ChatMessage>>()

    private val websocketDataRef = AtomicReference<List<WebsocketData>>()
    private val websocketDataProcessor = BehaviorProcessor.create<List<WebsocketData>>()

    init {
        echoService.observeEvent()
            .onEach { event ->
                val websocketState = when (event) {
                    is Event.OnLifecycle.StateChange<*> -> {
                        val state = when (event.state) {
                            Lifecycle.State.Started -> WebsocketState.OnLifecycleStateChangeStart
                            is Lifecycle.State.Stopped -> WebsocketState.OnLifecycleStateChangeStop
                            Lifecycle.State.Destroyed -> WebsocketState.OnLifecycleStateChangeTerminate
                        }
                        state
                    }
                    Event.OnLifecycle.Terminate -> {
                        WebsocketState.OnLifecycleTerminate
                    }
                    is Event.OnWebSocket.Event<*> -> when (event.event) {
                        is WebSocket.Event.OnConnectionOpened<*> -> {
                            WebsocketState.OnWebsocketEventConnectionOpened
                        }
                        is WebSocket.Event.OnMessageReceived -> {
                            val messageReceived = (event.event as WebSocket.Event.OnMessageReceived).message
                            messageReceived as Message.Text

                            val websocketData = getWebsocketData(messageReceived.value)

                            addWebsocketData(websocketData)
                            WebsocketState.OnWebsocketEventOnMessageReceived
                        }
                        is WebSocket.Event.OnConnectionClosing -> {
                            WebsocketState.OnWebsocketEventConnectionClosing
                        }
                        is WebSocket.Event.OnConnectionClosed -> {
                            WebsocketState.OnWebsocketEventConnectionClosed
                        }
                        is WebSocket.Event.OnConnectionFailed -> {
                            WebsocketState.OnWebsocketEventConnectionFailed
                        }
                    }
                    Event.OnWebSocket.Terminate -> {
                        WebsocketState.OnWebsocketTerminate
                    }
                    is Event.OnStateChange<*> -> {
                        val state = when (event.state) {
                            is State.WaitingToRetry -> {
                                WebsocketState.OnStateChangeWaitingToRetry
                            }
                            is State.Connecting -> {
                                WebsocketState.OnStateChangeConnecting
                            }
                            is State.Connected -> {
                                WebsocketState.OnStateChangeConnected
                            }
                            State.Disconnecting -> {
                                WebsocketState.OnStateChangeDisconnecting
                            }
                            State.Disconnected -> {
                                WebsocketState.OnStateChangeDisconnected
                            }
                            State.Destroyed -> {
                                WebsocketState.OnStateChangeDestroyed
                            }
                        }
                        state
                    }
                    Event.OnRetry -> WebsocketState.OnRetry
                }
//                addChatMessage(chatMessage)
            }.catch { e ->
                Timber.e(e)
            }.launchIn(coroutineScope)

        echoService.observeText()
            .onEach { text ->
                val chatMessage = ChatMessage.Text(
                    generateMessageId(),
                    text,
                    ChatMessage.Source.RECEIVED,
                    0
                )
                addChatMessage(chatMessage)
            }.catch { e ->
                Timber.e(e)
            }.launchIn(coroutineScope)
    }

    override fun observeChatMessage(): Flowable<List<ChatMessage>> = messagesProcessor

    override fun observeWebsocketData(): Flowable<List<WebsocketData>> = websocketDataProcessor

    override fun addTextMessage(text: String) {
        val chatMessage = ChatMessage.Text(generateMessageId(), text, ChatMessage.Source.SENT)
        addChatMessage(chatMessage)

        echoService.sendText(text)
    }

    override fun addImageMessage(bitmap: Bitmap) {
        val chatMessage = ChatMessage.Image(generateMessageId(), bitmap, ChatMessage.Source.SENT)
        addChatMessage(chatMessage)

        echoService.sendBitmap(bitmap)
    }

    override fun clearAllMessages() {
        messagesRef.set(listOf())
        messagesProcessor.onNext(listOf())
    }

    private fun addChatMessage(chatMessage: ChatMessage) {
        val existingMessages = messagesRef.get() ?: listOf()
        val messages = existingMessages + chatMessage
        messagesRef.set(messages)
        messagesProcessor.onNext(messages)
    }

    private fun addWebsocketData(websocketData: WebsocketData) {
        val existingMessages = websocketDataRef.get() ?: listOf()
        val messages = existingMessages + websocketData
        websocketDataRef.set(messages)
        websocketDataProcessor.onNext(messages)
    }

    private fun generateMessageId(): Int = messageCount.getAndIncrement()

    private fun getWebsocketData(messageData: String): WebsocketData {
        val websocketModel = moshi.adapter(WebsocketModel::class.java).fromJson(messageData)!!
        val webSocketCommand = WebSocketCommand.getWebSocketCommandBy(websocketModel.command)
        val webSocketChannel = WebSocketChannel.getWebSocketChannelBy(websocketModel.channel)
        val data = websocketModel.data
        return when(webSocketChannel) {
            WebSocketChannel.FriendChannel -> {
                val friendModel = moshi.adapter(FriendModel::class.java).fromJson(data)!!
                val friend = friendModelMapper.toData(friendModel)
                WebsocketData(
                    command = webSocketCommand,
                    channel = webSocketChannel,
                    friend = friend
                )
            }
            else -> {
                val friendModel = moshi.adapter(FriendModel::class.java).fromJson(data)!!
                val friend = friendModelMapper.toData(friendModel)
                WebsocketData(
                    command = webSocketCommand,
                    channel = webSocketChannel,
                    friend = friend
                )
            }
        }
    }

}
