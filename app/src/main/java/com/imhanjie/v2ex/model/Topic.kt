package com.imhanjie.v2ex.model

import com.google.gson.annotations.SerializedName

data class Topic(
    val id: Int,
    val member: Member,
    val node: Node,
    val content: String,
    @SerializedName("content_rendered")
    val contentRendered: String,
    val created: Int,
    @SerializedName("last_modified")
    val lastModified: Int,
    @SerializedName("last_reply_by")
    val lastReplyBy: String,
    @SerializedName("last_touched")
    val lastTouched: Int,
    val replies: Int,
    val title: String,
    val url: String
)

data class Member(
    val id: Int,
    @SerializedName("avatar_large")
    val avatarLarge: String,
    @SerializedName("avatar_mini")
    val avatarMini: String,
    @SerializedName("avatar_normal")
    val avatarNormal: String,
    val bio: Any,
    val btc: Any,
    val created: Int,
    val github: Any,
    val location: Any,
    val psn: Any,
    @SerializedName("tagline")
    val tagLine: Any,
    val twitter: Any,
    val url: String,
    val username: String,
    val website: Any
)

data class Node(
    val id: Int,
    val aliases: List<Any>,
    @SerializedName("avatar_large")
    val avatarLarge: String,
    @SerializedName("avatar_mini")
    val avatarMini: String,
    @SerializedName("avatar_normal")
    val avatarNormal: String,
    val footer: String,
    val header: String,
    val name: String,
    @SerializedName("parent_node_name")
    val parentNodeName: String,
    val root: Boolean,
    val stars: Int,
    val title: String,
    @SerializedName("title_alternative")
    val titleAlternative: String,
    val topics: Int,
    val url: String
)