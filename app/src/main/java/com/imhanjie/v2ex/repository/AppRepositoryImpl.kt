package com.imhanjie.v2ex.repository

import com.imhanjie.v2ex.App
import com.imhanjie.v2ex.api.ApiServer
import com.imhanjie.v2ex.api.ApiService
import com.imhanjie.v2ex.common.BizException
import com.imhanjie.v2ex.common.TopicTab
import com.imhanjie.v2ex.model.LoginInfo
import com.imhanjie.v2ex.model.Result
import com.imhanjie.v2ex.parser.model.MyUserInfo
import com.imhanjie.v2ex.parser.model.SignIn
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.parser.model.TopicItem
import java.io.InputStream

object AppRepositoryImpl : AppRepository {

    private val api: ApiService = ApiServer.create()

    /**
     * 错误处理
     */
    private fun <T> extractResult(result: Result<T>): T {
        if (result.code == Result.CODE_SUCCESS) {
            return result.data ?: throw BizException("data can not be null!")
        } else {
            if (result.code == Result.CODE_USER_EXPIRED) {
                App.launchLoginPage()
            }
            throw BizException(result.message!!)
        }
    }

    override suspend fun loadLatestTopics(tab: String, pageIndex: Int): List<TopicItem> {
        val result = if (tab == TopicTab.ALL.value) {
//            api.loadRecentTopics(pageIndex - 1)
            api.loadLatestTopics(tab)
        } else {
            api.loadLatestTopics(tab)
        }
        return extractResult(result)
    }

    override suspend fun loadNodeTopics(nodeTitle: String, pageIndex: Int): List<TopicItem> {
        return extractResult(api.loadNodeTopics(nodeTitle, pageIndex))
    }

    override suspend fun loadTopic(topicId: Long, pageIndex: Int): Topic {
//        api.loadTopic(topicId, pageIndex)
//        api.loadTopic(567112, pageIndex) // MY
//        api.loadTopic(419135, pageIndex) // PIC
//        api.loadTopic(670151, pageIndex) // SUBTLE
        return extractResult(api.loadTopic(topicId, pageIndex))
    }

    override suspend fun loadSignIn(): SignIn {
        return extractResult(api.loadSignIn())
    }

    override suspend fun loadImage(url: String): InputStream {
        return api.loadImage(url).byteStream()
    }

    override suspend fun login(signIn: SignIn, userName: String, password: String, verCode: String): LoginInfo {
        val result = api.login(
            mapOf(
                signIn.keyUserName to userName,
                signIn.keyPassword to password,
                signIn.keyVerCode to verCode,
                "once" to signIn.verificationUrl.split("=")[1],
                "next" to "/"
            )
        )
        return extractResult(result)
    }

    override suspend fun loadMyUserInfo(): MyUserInfo {
        return extractResult(api.loadMyUserInfo())
    }

}