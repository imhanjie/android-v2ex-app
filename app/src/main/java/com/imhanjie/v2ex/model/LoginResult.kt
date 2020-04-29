package com.imhanjie.v2ex.model

data class LoginResult(
    val success: Boolean,
    val cookie: String = "",
    val errorMsg: String = ""
)