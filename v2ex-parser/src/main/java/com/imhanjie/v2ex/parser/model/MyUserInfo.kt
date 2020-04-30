package com.imhanjie.v2ex.parser.model

data class MyUserInfo(
    val userName: String,
    val phone: String,
    val email: String,
    val avatar: String,
    val nodeStars: Long,
    val topicStars: Long,
    val following: Long
)