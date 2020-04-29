package com.imhanjie.v2ex.repository

import com.imhanjie.support.PreferencesManager
import com.imhanjie.v2ex.api.ApiServer
import com.imhanjie.v2ex.api.ApiService
import com.imhanjie.v2ex.common.SpConstants
import com.imhanjie.v2ex.common.TopicTab
import com.imhanjie.v2ex.model.LoginResult
import com.imhanjie.v2ex.parser.ParserImpl
import com.imhanjie.v2ex.parser.model.SignIn
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.parser.model.TopicItem
import okhttp3.ResponseBody

object AppRepositoryImpl : AppRepository {

    private val api: ApiService = ApiServer.create()

    override suspend fun loadLatestTopics(tab: String, pageIndex: Int): List<TopicItem> {
        val html: String
        if (tab == TopicTab.ALL.value) {
            html = api.loadRecentTopics(pageIndex - 1)
        } else {
            html = api.loadLatestTopics(tab)
        }
        return ParserImpl.parseLatestTopics(html)
    }

    override suspend fun loadNodeTopics(nodeTitle: String, pageIndex: Int): List<TopicItem> {
        val html = api.loadNodeTopics(nodeTitle, pageIndex)
        return ParserImpl.parseNodeTopics(html, nodeTitle, nodeTitle)
    }

    override suspend fun loadTopic(topicId: Long, pageIndex: Int): Topic {
//        val html = api.loadTopic(567112, pageIndex) // MY
//        val html = api.loadTopic(419135, pageIndex) // PIC
        val html = api.loadTopic(topicId, pageIndex)
        return ParserImpl.parserTopic(html)
    }

    override suspend fun loadSignIn(): SignIn {
        val html = api.loadSignIn()
        return ParserImpl.parserSignIn(html)
    }

    override suspend fun loadImage(url: String): ResponseBody {
        return api.loadImage(url)
    }

    override suspend fun login(signIn: SignIn, userName: String, password: String, verCode: String): LoginResult {
        val response = api.login(
            mapOf(
                signIn.keyUserName to userName,
                signIn.keyPassword to password,
                signIn.keyVerCode to verCode,
                "once" to signIn.verificationUrl.split("=")[1],
                "next" to "/"
            )
        )
        // 根据响应头中是否有 "A2" cookie 来判定是否登录成功
        response.raw().headers()
        val a2Cookie: String? = response.headers().values("set-cookie").firstOrNull { it.startsWith("A2=") }
        if (a2Cookie != null) {
            // 登录成功, 保存 cookie
            PreferencesManager.getInstance(SpConstants.FILE_COOKIES)
                .putString(SpConstants.COOKIE_A2, a2Cookie.toString())
            return LoginResult(
                success = true,
                cookie = a2Cookie
            )
        } else {
            // 登录失败，通过解析 html 寻找原因
            val html = response.body()!!.string()
            val problem = ParserImpl.parserSignInProblem(html)
            return LoginResult(
                success = false,
                errorMsg = problem
            )
        }
    }

}