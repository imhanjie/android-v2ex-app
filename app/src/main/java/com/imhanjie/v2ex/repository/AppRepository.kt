package com.imhanjie.v2ex.repository

import com.imhanjie.v2ex.parser.model.TopicItem

interface AppRepository {

    suspend fun loadLatestTopics(tab: String, pageIndex: Int): List<TopicItem>

    suspend fun loadNodeTopics(nodeTitle: String, pageIndex: Int): List<TopicItem>

}