package com.architecture.remote.websocket.util

import com.tinder.scarlet.Stream

class EmptyStreamObserver<in T> : Stream.Observer<T> {
    override fun onNext(data: T) {
    }

    override fun onComplete() {
    }

    override fun onError(throwable: Throwable) {
    }
}
