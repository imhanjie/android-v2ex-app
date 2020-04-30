package com.imhanjie.v2ex.model

data class Result<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
) {

    companion object {
        fun <T> success(data: T): Result<T> = Result(true, data)
        fun <T> fail(message: String): Result<T> = Result(false, null, message)
    }

}