package com.rtw.pro.baseline.data.common

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RetryExecutorTest {
    @Test
    fun execute_retriesOnRetryableHttpStatus() {
        var calls = 0
        val result = RetryExecutor.execute(RetryPolicy(maxAttempts = 2)) { _ ->
            calls += 1
            if (calls == 1) HttpResult.HttpError(503) else HttpResult.Success("ok")
        }
        assertTrue(result is HttpResult.Success)
        assertEquals(2, calls)
    }

    @Test
    fun execute_stopsOnNonRetryableHttpStatus() {
        var calls = 0
        val result = RetryExecutor.execute(RetryPolicy(maxAttempts = 3)) { _ ->
            calls += 1
            HttpResult.HttpError(400)
        }
        assertTrue(result is HttpResult.HttpError)
        assertEquals(1, calls)
    }
}
