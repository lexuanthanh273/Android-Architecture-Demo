package com.architecture.remote

import java.io.IOException

sealed class NetworkResponse<out R : Any, out E : Any> {
    /**
     * Success response with body
     */
    data class Success<R : Any>(val body: R) : NetworkResponse<R, Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError<E : Any>(val body: E, val httpCode: Int) : NetworkResponse<Nothing, E>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing, Nothing>()
}

typealias GenericResponse<R> = NetworkResponse<R, GenericApiError>
