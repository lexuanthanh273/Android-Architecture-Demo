package com.architecture.core.network

import com.architecture.domain.UiResource

object HttpExceptionManager {

    fun getHttpException(httpCode: Int, businessCode: Int) = when(httpCode) {
        BadRequestException.httpCode -> BadRequestException
        UnauthorizedException.httpCode -> UnauthorizedException
        ForbiddenException.httpCode -> ForbiddenException
        NotFoundException.httpCode -> NotFoundException
        422 -> BusinessException(businessCode)
        InternalServerErrorException.httpCode -> InternalServerErrorException
        NotImplementedException.httpCode -> NotImplementedException
        ServiceUnavailableException.httpCode -> ServiceUnavailableException
        RequestTimeoutException.httpCode -> RequestTimeoutException
        NetworkAuthenticationException.httpCode -> NetworkAuthenticationException
        else -> UnknownException
    }

    object NoConnectionException: UiResource.Error.HttpException {
        override val httpCode: Int
            get() = -1
    }

    object BadRequestException: UiResource.Error.HttpException {
        override val httpCode: Int
            get() = 400
    }

    object UnauthorizedException: UiResource.Error.HttpException {
        override val httpCode: Int
            get() = 401
    }

    object ForbiddenException: UiResource.Error.HttpException {
        override val httpCode: Int
            get() = 403
    }

    object NotFoundException: UiResource.Error.HttpException {
        override val httpCode: Int
            get() = 404
    }

    object RequestTimeoutException: UiResource.Error.HttpException {
        override val httpCode: Int
            get() = 408
    }

    class BusinessException(code: Int): UiResource.Error.HttpException {
        override val httpCode: Int
            get() = 422

        val businessError = when(code) {
            else -> BusinessError.Unknown
        }

    }

    object InternalServerErrorException: UiResource.Error.HttpException {
        override val httpCode: Int
            get() = 500
    }

    object NotImplementedException: UiResource.Error.HttpException {
        override val httpCode: Int
            get() = 501
    }

    object ServiceUnavailableException: UiResource.Error.HttpException {
        override val httpCode: Int
            get() = 503
    }

    object NetworkAuthenticationException: UiResource.Error.HttpException {
        override val httpCode: Int
            get() = 511
    }

    object UnknownException: UiResource.Error.HttpException {
        override val httpCode: Int
            get() = -2
    }
}



