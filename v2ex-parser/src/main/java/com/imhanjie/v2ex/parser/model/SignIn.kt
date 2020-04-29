package com.imhanjie.v2ex.parser.model

data class SignIn(
    val keyUserName: String,
    val keyPassword: String,
    val keyVerCode: String,
    val verificationUrl: String
)
