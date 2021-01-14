package com.myapplication.newsapps.util

import androidx.annotation.NonNull
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.ResponseBody
import okio.BufferedSource
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
object RetrofitTestUtil {
    fun <T> createCall(response: T): Call<T> {
        return MockCall(response)
    }

    fun <T> createCall(responseCode: Int, response: T): Call<T> {
        return MockCall(responseCode, response)
    }

    private class MockCall<T>(val mCode: Int, val mResponse: T) : Call<T> {
        constructor(response: T) : this(200, response) {}

        @Throws(IOException::class)
        override fun execute(): Response<T> {
            return buildResponse()
        }

        override fun isExecuted(): Boolean {
            return false
        }

        override fun isCanceled(): Boolean {
            return false
        }

        override fun request(): Request? {
            return null
        }

        @NonNull
        private fun buildResponse(): Response<T> {
            return if (mCode > 199 && mCode < 300) {
                Response.success(mResponse)
            } else {
                Response.error(mCode, DummyResponseBody())
            }
        }

        override fun enqueue(callback: Callback<T>) {
            if (mCode > 0) {
                callback.onResponse(null, buildResponse())
            } else {
                callback.onFailure(null, null)
            }
        }

        override fun cancel() {}
        override fun clone(): Call<T> {
            return MockCall(mResponse)
        }

        override fun timeout(): Timeout {
            TODO("Not yet implemented")
        }
    }

    private class DummyResponseBody : ResponseBody() {
        override fun contentType(): MediaType? {
            return null
        }

        override fun source(): BufferedSource {
            TODO("Not yet implemented")
        }

        override fun contentLength(): Long {
            return 0
        }

    }
}