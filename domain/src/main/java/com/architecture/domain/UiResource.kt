package com.architecture.domain


sealed class UiResource<T> {

    data class Success<T>(
        val data: T
    ) : UiResource<T>()

    data class Error<T>(
        val httpException: HttpException,
        val data: T?,
        val message: String,
    ) : UiResource<T>() {
        interface HttpException {
            val httpCode: Int
        }
    }

    class Loading<T>(
        val data: T? = null
    ) : UiResource<T>()
}
