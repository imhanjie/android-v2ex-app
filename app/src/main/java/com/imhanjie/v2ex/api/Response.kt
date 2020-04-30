package com.imhanjie.v2ex.api

import com.imhanjie.support.parseToJson
import com.imhanjie.v2ex.model.Result
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody

fun Response.recreateSuccessJsonResponse(data: Any): Response {
    val responseBody = ResponseBody.create(
        MediaType.parse("application/json;charset=UTF-8"),
        parseToJson(Result.success(data))
    )
    return this.newBuilder().code(200).body(responseBody).build()
}

fun Response.recreateFailJsonResponse(message: String): Response {
    val responseBody = ResponseBody.create(
        MediaType.parse("application/json;charset=UTF-8"),
        parseToJson(Result.fail<String>(message))
    )
    return this.newBuilder().code(200).body(responseBody).build()
}