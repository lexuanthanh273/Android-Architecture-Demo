@file:JvmName("FlowableUtils")

package com.architecture.remote.websocket.util

import io.reactivex.Flowable

fun <T> Flowable<T>.toStream() = FlowableStream(this)
