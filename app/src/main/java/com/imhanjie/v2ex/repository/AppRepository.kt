package com.imhanjie.v2ex.repository

import com.imhanjie.v2ex.model.LoginInfo
import com.imhanjie.v2ex.parser.model.MyUserInfo
import com.imhanjie.v2ex.parser.model.SignIn
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.parser.model.TopicItem
import java.io.InputStream

interface AppRepository {

    suspend fun loadLatestTopics(tab: String, pageIndex: Int): List<TopicItem>

    suspend fun loadNodeTopics(nodeTitle: String, pageIndex: Int): List<TopicItem>

    suspend fun loadTopic(topicId: Long, pageIndex: Int): Topic

    suspend fun loadSignIn(): SignIn

    suspend fun loadImage(url: String): InputStream

    suspend fun login(signIn: SignIn, userName: String, password: String, verCode: String): LoginInfo

    suspend fun loadMyUserInfo(): MyUserInfo

}