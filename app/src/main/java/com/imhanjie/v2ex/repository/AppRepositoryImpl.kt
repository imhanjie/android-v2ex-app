package com.imhanjie.v2ex.repository

import com.imhanjie.v2ex.api.ApiServer
import com.imhanjie.v2ex.api.ApiService
import com.imhanjie.v2ex.parser.ParserImpl
import com.imhanjie.v2ex.parser.model.TopicItem

object AppRepositoryImpl : AppRepository {

    private val api: ApiService = ApiServer.create()

    override suspend fun loadLatestTopics(pageIndex: Int): List<TopicItem> {
        val html =
            if (pageIndex == 1) api.loadLatestTopics() else api.loadRecentTopics(pageIndex - 1)
        return ParserImpl.parseLatestTopics(html)
    }

    override suspend fun loadNodeTopics(nodeTitle: String, pageIndex: Int): List<TopicItem> {
        val html = api.loadNodeTopics(nodeTitle, pageIndex)
        return ParserImpl.parseNodeTopics(html, nodeTitle, nodeTitle)
    }

}