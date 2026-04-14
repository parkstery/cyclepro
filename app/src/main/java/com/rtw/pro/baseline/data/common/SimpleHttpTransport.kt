package com.rtw.pro.baseline.data.common

data class HttpRequest(
    val url: String,
    val method: String = "GET"
)

data class HttpResponse(
    val statusCode: Int,
    val body: String
)

interface SimpleHttpTransport {
    fun execute(request: HttpRequest): HttpResult<HttpResponse>
}
