package com.tamiang.smart.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request : Request = chain.request()
            .newBuilder()
            .addHeader("content-type", "application/json")
            .addHeader("X-key", "majumundurok")
            .build()

        return chain.proceed(request)
    }
}