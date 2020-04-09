package com.imhanjie.v2ex.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val REQUEST_TIME_OUT = 15000L

object ApiServer {

    val okHttpClient: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            builder
                .connectTimeout(REQUEST_TIME_OUT, TimeUnit.MILLISECONDS)
            return builder.build()
        }

    inline fun <reified T> create(): T {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.v2ex.com/api/")
            .build()
            .create(T::class.java)
    }

}