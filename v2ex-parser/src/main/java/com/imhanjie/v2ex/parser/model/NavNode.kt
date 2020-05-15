package com.imhanjie.v2ex.parser.model

data class NavNode(
    val type: String,
    val children: List<TinyNode>
)