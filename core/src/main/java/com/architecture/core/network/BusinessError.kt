package com.architecture.core.network

sealed class BusinessError {
    abstract val code: Int

    object Unknown: BusinessError() {
        override val code = -1
    }
}