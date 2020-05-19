package com.imhanjie.v2ex.parser.model

data class Reply(
    val id: Long,
    val userAvatar: String,
    val userName: String,
    val content: String,
    val time: String,
    var thankCount: Long,
    var thanked: Boolean,
    val no: Long
)