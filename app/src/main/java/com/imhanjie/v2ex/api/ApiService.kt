package com.imhanjie.v2ex.api

import com.imhanjie.v2ex.model.LoginInfo
import com.imhanjie.v2ex.model.Result
import com.imhanjie.v2ex.parser.model.MyUserInfo
import com.imhanjie.v2ex.parser.model.SignIn
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.parser.model.TopicItem
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

    @GET("/go/{node_title}")
    suspend fun loadNodeTopics(
        @Path("node_title") nodeTitle: String,
        @Query("p") pageIndex: Int
    ): Result<List<TopicItem>>

    @GET("/t/{topic_id}")
    suspend fun loadTopic(
        @Path("topic_id") id: Long,
        @Query("p") pageIndex: Int
    ): Result<Topic>

    @GET("/signin")
    suspend fun loadSignIn(): Result<SignIn>

    @GET
    suspend fun loadImage(
        @Url url: String
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

}