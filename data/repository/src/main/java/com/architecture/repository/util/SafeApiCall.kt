package com.architecture.repository.util

import com.architecture.core.network.HttpExceptionManager
import com.architecture.domain.UiResource
import com.architecture.remote.GenericApiResponse
import com.architecture.remote.NetworkResponse
import com.architecture.remote.GenericApiError

inline fun <M : Any, D : Any> NetworkResponse<GenericApiResponse<M>, GenericApiError>.toUiResource(
    crossinline mapperModelToData: (M) -> D
): UiResource<D> {
    return when (this) {
        is NetworkResponse.Success -> {
            UiResource.Success(mapperModelToData.invoke(this.body.data!!))
        }
        is NetworkResponse.ApiError -> UiResource.Error(
            HttpExceptionManager.getHttpException(
                httpCode = this.httpCode,
                businessCode = this.body.code
            ),
            null,
            this.body.message
        )
        is NetworkResponse.NetworkError -> UiResource.Error(HttpExceptionManager.NoConnectionException, null, "")
        is NetworkResponse.UnknownError ->  UiResource.Error(
            HttpExceptionManager.UnknownException, null,
            this.error?.message.toString()
        )
    }
}

inline fun <M : Any, D : Any> NetworkResponse<GenericApiResponse<M?>, GenericApiError>.toUiResourceNullable(
    crossinline mapperModelToData: (M) -> D
): UiResource<D?> {
    return when (this) {
        is NetworkResponse.Success -> {
            if (this.body.data == null) {
                UiResource.Success(null)
            } else {
                UiResource.Success(mapperModelToData.invoke(this.body.data!!))
            }

        }
        is NetworkResponse.ApiError -> UiResource.Error(
            HttpExceptionManager.getHttpException(
                httpCode = this.httpCode,
                businessCode = this.body.code
            ),
            null,
            this.body.message
        )
        is NetworkResponse.NetworkError -> UiResource.Error(HttpExceptionManager.NoConnectionException, null, "")
        is NetworkResponse.UnknownError ->  UiResource.Error(
            HttpExceptionManager.UnknownException, null,
            this.error?.message.toString()
        )
    }
}
