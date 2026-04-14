package com.rtw.pro.baseline.data.common

object RetryExecutor {
    fun <T> execute(
        policy: RetryPolicy,
        block: (attempt: Int) -> HttpResult<T>
    ): HttpResult<T> {
        var attempt = 1
        var last: HttpResult<T> = HttpResult.NetworkError("not-executed")
        while (true) {
            val result = block(attempt)
            when (result) {
                is HttpResult.Success -> return result
                is HttpResult.HttpError -> {
                    last = result
                    if (!policy.canRetry(attempt) || !policy.isRetryableStatus(result.statusCode)) {
                        return result
                    }
                }
                is HttpResult.NetworkError -> {
                    last = result
                    if (!policy.canRetry(attempt)) return result
                }
            }
            attempt += 1
            if (attempt > policy.maxAttempts) return last
        }
    }
}
