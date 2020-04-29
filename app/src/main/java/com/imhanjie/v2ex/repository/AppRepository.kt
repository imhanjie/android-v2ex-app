package com.imhanjie.v2ex.repository

import com.imhanjie.v2ex.model.LoginResult
import com.imhanjie.v2ex.parser.model.SignIn
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.parser.model.TopicItem
import okhttp3.ResponseBody

interface AppRepository {

    suspend fun loadLatestTopics(tab: String, pageIndex: Int): List<TopicItem>

    suspend fun loadNodeTopics(nodeTitle: String, pageIndex: Int): List<TopicItem>

    suspend fun loadTopic(topicId: Long, pageIndex: Int): Topic

    suspend fun loadSignIn(): SignIn

    suspend fun loadImage(url: String): ResponseBody

    suspend fun login(signIn: SignIn, userName: String, password: String, verCode: String): LoginResult

}