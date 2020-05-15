package com.imhanjie.v2ex.repository

import com.imhanjie.v2ex.model.LoginInfo
import com.imhanjie.v2ex.parser.model.*
import java.io.InputStream

interface AppRepository {

    suspend fun loadLatestTopics(tab: String, pageIndex: Int): List<TopicItem>

    suspend fun loadNodeTopics(nodeName: String, pageIndex: Int): Node

    suspend fun loadTopic(topicId: Long, pageIndex: Int): Topic

    suspend fun loadSignIn(): SignIn

    suspend fun loadVerImage(once: String): InputStream

    suspend fun login(signIn: SignIn, userName: String, password: String, verCode: String): LoginInfo

    suspend fun loadMyUserInfo(): MyUserInfo

    suspend fun loadAllNode(): List<TinyNode>

    suspend fun favoriteNode(nodeId: Long, once: String): Any

    suspend fun unFavoriteNode(nodeId: Long, once: String): Any

    suspend fun loadFavoriteNodes(): List<MyNode>

    suspend fun loadNotifications(pageIndex: Int): Notifications

}