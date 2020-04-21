package com.imhanjie.v2ex.api

import com.imhanjie.v2ex.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

private const val REQUEST_TIME_OUT = 15000L

object ApiServer {

    val okHttpClient: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            builder
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG)
                            HttpLoggingInterceptor.Level.BASIC
                        else
                            HttpLoggingInterceptor.Level.NONE
                })
                .connectTimeout(REQUEST_TIME_OUT, TimeUnit.MILLISECONDS)
            return builder.build()
        }

    inline fun <reified T> create(): T {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.v2ex.com/")
            .build()
            .create(T::class.java)
    }

}