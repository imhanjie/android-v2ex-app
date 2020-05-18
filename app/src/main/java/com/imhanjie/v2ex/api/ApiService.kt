package com.imhanjie.v2ex.api

import com.imhanjie.v2ex.model.LoginInfo
import com.imhanjie.v2ex.parser.model.*
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {

    @GET("/")
    suspend fun loadLatestTopics(
        @Query("tab") tab: String
    ): Result<List<TopicItem>>

    @GET("/recent")
    suspend fun loadRecentTopics(
        @Query("p") pageIndex: Int
    ): Result<List<TopicItem>>

    @GET("/go/{node_name}")
    suspend fun loadNodeTopics(
        @Path("node_name") nodeName: String,
        @Query("p") pageIndex: Int
    ): Result<Node>

    @GET("/t/{topic_id}")
    suspend fun loadTopic(
        @Path("topic_id") id: Long,
        @Query("p") pageIndex: Int
    ): Result<Topic>

    @GET("/signin")
    suspend fun loadSignIn(): Result<SignIn>

    @GET("/_captcha")
    @Headers(
        "Referer: ${ApiServer.BASE_URL}/signin"
    )
    suspend fun loadVerImage(
        @Query("once") once: String
    ): ResponseBody

    @POST("/signin")
    @FormUrlEncoded
    @Headers(
        "Origin: ${ApiServer.BASE_URL}",
        "Referer: ${ApiServer.BASE_URL}/signin"
    )
    suspend fun login(
        @FieldMap fields: Map<String, String>
    ): Result<LoginInfo>

    @GET("/settings")
    suspend fun loadMyUserInfo(): Result<MyUserInfo>

    @GET("/planes")
    suspend fun loadAllNode(): Result<List<TinyNode>>

    @GET("/favorite/node/{node_id}")
    @Headers(
        "Referer: ${ApiServer.BASE_URL}/go"
    )
    suspend fun favoriteNode(
        @Path("node_id") nodeId: Long,
        @Query("once") once: String
    ): Result<Any>

    @GET("/unfavorite/node/{node_id}")
    @Headers(
        "Referer: ${ApiServer.BASE_URL}/go"
    )
    suspend fun unFavoriteNode(
        @Path("node_id") nodeId: Long,
        @Query("once") once: String
    ): Result<Any>

    @GET("/my/nodes")
    suspend fun loadFavoriteNodes(): Result<List<MyNode>>

    @GET("/notifications")
    suspend fun loadNotifications(
        @Query("p") pageIndex: Int
    ): Result<Notifications>

    @POST("/thank/reply/{reply_id}")
    suspend fun thankReply(
        @Path("reply_id") replyId: Long,
        @Query("once") once: String
    ): Result<V2exResult>

}