package com.theapache64.twinkill.network.utils.retrofit.interceptors

import okhttp3.Interceptor
import okhttp3.Response

abstract class BaseHeaderInterceptor : Interceptor {

    abstract fun getKey(): String
    abstract fun getValue(): String?

    override fun intercept(chain: Interceptor.Chain): Response {

        // Creating request
        val request = chain
            .request()
            .newBuilder()

        getValue()?.let { value ->
            // adding header
            request.addHeader(getKey(), value)
        }

        return chain.proceed(request.build())
    }
}