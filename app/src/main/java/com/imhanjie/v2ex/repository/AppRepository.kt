package com.imhanjie.v2ex.repository

import com.imhanjie.v2ex.api.model.*
import com.imhanjie.v2ex.model.SearchNode
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

    suspend fun loadAllNodeForSearch(): List<SearchNode>

    suspend fun favoriteNode(nodeId: Long, once: String): Any

    suspend fun unFavoriteNode(nodeId: Long, once: String): Any

    suspend fun loadFavoriteNodes(): List<MyNode>

    suspend fun loadNotifications(pageIndex: Int): Notifications

    suspend fun loadNavNodes(): List<NavNode>

    suspend fun thankReply(replyId: Long, once: String): V2exResult

    suspend fun ignoreTopic(topicId: Long, once: String): Any

    suspend fun favoriteTopic(topicId: Long, favoriteParam: String): Topic

    suspend fun unFavoriteTopic(topicId: Long, favoriteParam: String): Topic

    suspend fun loadFavoriteTopics(pageIndex: Int): FavoriteTopics

    suspend fun loadMember(userName: String): Member

    suspend fun followMember(userId: Long, userName: String, once: String): Member

    suspend fun unFollowMember(userId: Long, userName: String, once: String): Member

    suspend fun blockMember(userId: Long, userName: String, t: String): Member

    suspend fun unBlockMember(userId: Long, userName: String, t: String): Member

    suspend fun loadMemberTopics(userName: String, pageIndex: Int): MemberTopics

    suspend fun loadMemberReplies(userName: String, pageIndex: Int): MemberReplies

    suspend fun previewTopicContent(content: String): String

}