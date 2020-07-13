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
    suspend fun loadSignIn(): RestfulResult<SignInInfo>

    @GET("/_captcha")
    suspend fun loadVerImage(
        @Query("once") once: String
    ): ResponseBody

    @POST("/signin")
    @FormUrlEncoded
    suspend fun login(
        @FieldMap fields: Map<String, String>
    ): RestfulResult<LoginInfo>

    @GET("/settings/privacy")
    suspend fun loadMyUserInfo(): RestfulResult<MyUserInfo>

    @GET("/planes")
    suspend fun loadAllNode(): RestfulResult<List<TinyNode>>

    @GET("/api/nodes/s2.json")
    suspend fun loadAllNodeForSearch(): List<SearchNode>

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

    @GET("/member/{user_name}")
    suspend fun loadMember(
        @Path("user_name") userName: String
    ): RestfulResult<Member>

    @GET("/follow/{user_id}")
    suspend fun followMember(
        @Path("user_id") userId: Long,
        @Query("userName") userName: String,
        @Query("once") once: String
    ): RestfulResult<Member>

    @GET("/unfollow/{user_id}")
    suspend fun unFollowMember(
        @Path("user_id") userId: Long,
        @Query("userName") userName: String,
        @Query("once") once: String
    ): RestfulResult<Member>

    @GET("/block/{user_id}")
    suspend fun blockMember(
        @Path("user_id") userId: Long,
        @Query("userName") userName: String,
        @Query("t") t: String
    ): RestfulResult<Member>

    @GET("/unblock/{user_id}")
    suspend fun unBlockMember(
        @Path("user_id") userId: Long,
        @Query("userName") userName: String,
        @Query("t") t: String
    ): RestfulResult<Member>

    @GET("/member/{user_name}/topics")
    suspend fun loadMemberTopics(
        @Path("user_name") userName: String,
        @Query("p") pageIndex: Int
    ): RestfulResult<MemberTopics>

    @GET("/member/{user_name}/replies")
    suspend fun loadMemberReplies(
        @Path("user_name") userName: String,
        @Query("p") pageIndex: Int
    ): RestfulResult<MemberReplies>

    @POST("/preview/markdown")
    @FormUrlEncoded
    suspend fun previewTopicContent(
        @Field("md") content: String
    ): String

    @GET("/new")
    suspend fun loadCreateTopicInfo(): RestfulResult<CreateTopicInfo>

    @POST("/new")
    @FormUrlEncoded
    suspend fun createTopic(
        @Field("title") title: String,
        @Field("content") content: String,
        @Field("node_name") nodeName: String,
        @Field("once") once: String
    ): RestfulResult<Long>

    @GET("/append/topic/{topic_id}")
    suspend fun loadAppendTopicInfo(
        @Path("topic_id") topicId: Long
    ): RestfulResult<AppendTopicInfo>

    @POST("/append/topic/{topic_id}")
    @FormUrlEncoded
    suspend fun appendTopic(
        @Path("topic_id") topicId: Long,
        @Field("content") content: String,
        @Field("syntax") syntax: Int,
        @Field("once") once: String
    ): RestfulResult<Any>

}