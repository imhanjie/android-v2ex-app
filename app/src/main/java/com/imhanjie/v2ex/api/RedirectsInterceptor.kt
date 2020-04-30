package com.imhanjie.v2ex.api

import okhttp3.Interceptor
import okhttp3.Response

class RedirectsInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code() == 302) {
            val redirectUrl = ApiServer.BASE_URL + response.header("location")
            request.newBuilder()
        }
        return response
    }

}