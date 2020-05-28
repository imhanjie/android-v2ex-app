package com.imhanjie.v2ex.repository

import com.imhanjie.support.parseJsonList
import com.imhanjie.v2ex.App
import com.imhanjie.v2ex.api.ApiServer
import com.imhanjie.v2ex.api.ApiService
import com.imhanjie.v2ex.api.model.*
import com.imhanjie.v2ex.common.BizException
import com.imhanjie.v2ex.common.TopicTab
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

object AppRepositoryImpl : AppRepository {

    private val api: ApiService = ApiServer.create()

    /**
     * 错误处理
     */
    private fun <T> RestfulResult<T>.extract(): T {
        if (code == RestfulResult.CODE_SUCCESS) {
            return data ?: throw BizException("data can not be null!")
        } else {
            if (code == RestfulResult.CODE_USER_EXPIRED) {
                App.logout()
            }
            throw BizException(message!!)
        }
    }

    override suspend fun loadLatestTopics(tab: String, pageIndex: Int): List<TopicItem> {
        val result = if (tab == TopicTab.ALL.value) {
//            api.loadRecentTopics(pageIndex - 1)
            api.loadLatestTopics(tab)
        } else {
            api.loadLatestTopics(tab)
        }
        return result.extract()
    }

    override suspend fun loadNodeTopics(nodeName: String, pageIndex: Int): Node {
        return api.loadNodeTopics(nodeName, pageIndex).extract()
    }

    override suspend fun loadTopic(topicId: Long, pageIndex: Int): Topic {
//        api.loadTopic(topicId, pageIndex)
//        api.loadTopic(567112, pageIndex) // MY
//        api.loadTopic(419135, pageIndex) // PIC
//        api.loadTopic(670151, pageIndex) // SUBTLE
//        api.loadTopic(671006, pageIndex) // TEST
        return api.loadTopic(topicId, pageIndex).extract()
    }

    override suspend fun loadSignIn(): SignIn {
        return api.loadSignIn().extract()
    }

    override suspend fun loadVerImage(once: String): InputStream {
        return api.loadVerImage(once).byteStream()
    }

    override suspend fun login(signIn: SignIn, userName: String, password: String, verCode: String): LoginInfo {
        val result = api.login(
            mapOf(
                signIn.keyUserName to userName,
                signIn.keyPassword to password,
                signIn.keyVerCode to verCode,
                "once" to signIn.verUrlOnce,
                "next" to "/"
            )
        )
        return result.extract()
    }

    override suspend fun loadMyUserInfo(): MyUserInfo {
        return api.loadMyUserInfo().extract()
    }

    override suspend fun loadAllNode(): List<TinyNode> {
        return api.loadAllNode().extract()
    }

    override suspend fun favoriteNode(nodeId: Long, once: String): Any {
        return api.favoriteNode(nodeId, once).extract()
    }

    override suspend fun unFavoriteNode(nodeId: Long, once: String): Any {
        return api.unFavoriteNode(nodeId, once).extract()
    }

    override suspend fun loadFavoriteNodes(): List<MyNode> {
        return api.loadFavoriteNodes().extract()
    }

    override suspend fun loadNotifications(pageIndex: Int): Notifications {
        return api.loadNotifications(pageIndex).extract()
    }

    override suspend fun loadNavNodes(): List<NavNode> {
        return withContext(Dispatchers.IO) {
            val assetManager = App.INSTANCE.assets
            val json = assetManager.open("nav_nodes.json").bufferedReader().readText()
            parseJsonList<NavNode>(json)
        }
    }

    override suspend fun thankReply(replyId: Long, once: String): V2exResult {
        return api.thankReply(replyId, once).extract()
    }

    override suspend fun ignoreTopic(topicId: Long, once: String): Any {
        return api.ignoreTopic(topicId, once).extract()
    }

    override suspend fun favoriteTopic(topicId: Long, favoriteParam: String): Topic {
        return api.favoriteTopic(topicId, favoriteParam).extract()
    }

    override suspend fun unFavoriteTopic(topicId: Long, favoriteParam: String): Topic {
        return api.unFavoriteTopic(topicId, favoriteParam).extract()
    }

    override suspend fun loadFavoriteTopics(pageIndex: Int): FavoriteTopics {
        return api.loadFavoriteTopics(pageIndex).extract()
    }

    override suspend fun loadMember(userName: String): Member {
        return api.loadMember(userName).extract()
    }

    override suspend fun followMember(userId: Long, userName: String, once: String): Member {
        return api.followMember(userId, userName, once).extract()
    }

    override suspend fun unFollowMember(userId: Long, userName: String, once: String): Member {
        return api.unFollowMember(userId, userName, once).extract()
    }

    override suspend fun blockMember(userId: Long, userName: String, t: String): Member {
        return api.blockMember(userId, userName, t).extract()
    }

    override suspend fun unBlockMember(userId: Long, userName: String, t: String): Member {
        return api.unBlockMember(userId, userName, t).extract()
    }

}