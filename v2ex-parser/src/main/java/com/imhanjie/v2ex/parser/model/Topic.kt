package com.imhanjie.v2ex.parser.model

data class Topic(
    val id: Long,
    val title: String,
    val nodeName: String,
    val nodeTitle: String,
    val userName: String,
    val userAvatar: String,
    val createTime: String,
    val click: Long,
    val content: String,
    val replies: List<Reply>
)