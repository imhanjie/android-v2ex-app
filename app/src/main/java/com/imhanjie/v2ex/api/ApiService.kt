package com.imhanjie.v2ex.api

import com.imhanjie.v2ex.model.Result
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.parser.model.TopicItem
import okhttp3.ResponseBody
import retrofit2.Response
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
    suspend fun loadSignIn(): String

    @GET
    suspend fun loadImage(
        @Url url: String
    ): ResponseBody

    @POST("/signin")
    @FormUrlEncoded
    @Headers(
        "Origin: https://v2ex.com",
        "Referer: https://v2ex.com/signin"
    )
    suspend fun login(
        @FieldMap fields: Map<String, String>
    ): Response<ResponseBody>

}