package com.imhanjie.v2ex.api

import com.imhanjie.v2ex.api.model.*
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {

    @GET("/")
    suspend fun loadLatestTopics(
        @Query("tab") tab: String
    ): RestfulResult<List<TopicItem>>

    @GET("/recent")
    suspend fun loadRecentTopics(
        @Query("p") pageIndex: Int
    ): RestfulResult<List<TopicItem>>

    @GET("/go/{node_name}")
    suspend fun loadNodeTopics(
        @Path("node_name") nodeName: String,
        @Query("p") pageIndex: Int
    ): RestfulResult<Node>

    @GET("/t/{topic_id}")
    suspend fun loadTopic(
        @Path("topic_id") id: Long,
        @Query("p") pageIndex: Int
    ): RestfulResult<Topic>

    @GET("/signin")
    suspend fun loadSignIn(): RestfulResult<SignIn>

    @GET("/_captcha")
    suspend fun loadVerImage(
        @Query("once") once: String
    ): ResponseBody

    @POST("/signin")
    @FormUrlEncoded
    suspend fun login(
        @FieldMap fields: Map<String, String>
    ): RestfulResult<LoginInfo>

    @GET("/settings")
    suspend fun loadMyUserInfo(): RestfulResult<MyUserInfo>

    @GET("/planes")
    suspend fun loadAllNode(): RestfulResult<List<TinyNode>>

    @GET("/favorite/node/{node_id}")
    suspend fun favoriteNode(
        @Path("node_id") nodeId: Long,
        @Query("once") once: String
    ): RestfulResult<Any>

    @GET("/unfavorite/node/{node_id}")
    suspend fun unFavoriteNode(
        @Path("node_id") nodeId: Long,
        @Query("once") once: String
    ): RestfulResult<Any>

    @GET("/my/nodes")
    suspend fun loadFavoriteNodes(): RestfulResult<List<MyNode>>

    @GET("/notifications")
    suspend fun loadNotifications(
        @Query("p") pageIndex: Int
    ): RestfulResult<Notifications>

    @POST("/thank/reply/{reply_id}")
    suspend fun thankReply(
        @Path("reply_id") replyId: Long,
        @Query("once") once: String
    ): RestfulResult<V2exResult>

    @GET("/ignore/topic/{topic_id}")
    suspend fun ignoreTopic(
        @Path("topic_id") topicId: Long,
        @Query("once") once: String
    ): RestfulResult<Any>

    @GET("/favorite/topic/{topic_id}")
    suspend fun favoriteTopic(
        @Path("topic_id") topicId: Long,
        @Query("t") favoriteParam: String
    ): RestfulResult<Topic>

    @GET("/unfavorite/topic/{topic_id}")
    suspend fun unFavoriteTopic(
        @Path("topic_id") topicId: Long,
        @Query("t") favoriteParam: String
    ): RestfulResult<Topic>

    @GET("/my/topics")
    suspend fun loadFavoriteTopics(
        @Query("p") pageIndex: Int
    ): RestfulResult<FavoriteTopics>

}