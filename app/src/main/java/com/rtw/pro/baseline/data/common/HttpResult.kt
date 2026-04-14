package com.rtw.pro.baseline.data.common

sealed class HttpResult<out T> {
    data class Success<T>(val value: T) : HttpResult<T>()
    data class HttpError(val statusCode: Int, val body: String? = null) : HttpResult<Nothing>()
    data class NetworkError(val message: String) : HttpResult<Nothing>()
}
