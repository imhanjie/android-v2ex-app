package com.imhanjie.v2ex.parser

import com.imhanjie.v2ex.parser.model.SignIn
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.parser.model.TopicItem

interface Parser {

    fun parseLatestTopics(html: String): List<TopicItem>

    fun parseNodeTopics(
        html: String,
        nodeName: String,
        nodeTitle: String
    ): List<TopicItem>

    fun parserTopic(html: String): Topic

    fun parserSignIn(html: String): SignIn

    fun parserSignInProblem(html: String): String?

}