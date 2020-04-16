package com.imhanjie.v2ex.parser

import com.imhanjie.v2ex.parser.model.TopicItem

interface Parser {

    fun parseLatestTopics(html: String): List<TopicItem>

    fun parseNodeTopics(
        html: String,
        nodeName: String,
        nodeTitle: String
    ): List<TopicItem>

}