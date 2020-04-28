package com.imhanjie.v2ex.repository

import com.imhanjie.v2ex.api.ApiServer
import com.imhanjie.v2ex.api.ApiService
import com.imhanjie.v2ex.parser.ParserImpl
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.parser.model.TopicItem

object AppRepositoryImpl : AppRepository {

    private val api: ApiService = ApiServer.create()

    override suspend fun loadLatestTopics(tab: String, pageIndex: Int): List<TopicItem> {
        val html: String
//        if (tab == TopicTab.ALL.value) {
        html = if (pageIndex == 1) api.loadLatestTopics(tab) else api.loadRecentTopics(pageIndex - 1)
//        } else {
//            html = api.loadNodeTopics(tab, pageIndex)
//        }
        return ParserImpl.parseLatestTopics(html)
    }

    override suspend fun loadNodeTopics(nodeTitle: String, pageIndex: Int): List<TopicItem> {
        val html = api.loadNodeTopics(nodeTitle, pageIndex)
        return ParserImpl.parseNodeTopics(html, nodeTitle, nodeTitle)
    }

    override suspend fun loadTopic(topicId: Long, pageIndex: Int): Topic {
//        val html = api.loadTopic(567112, pageIndex) // MY
        val html = api.loadTopic(419135, pageIndex) // PIC
//        val html = api.loadTopic(topicId, pageIndex)
        return ParserImpl.parserTopic(html)
    }

}