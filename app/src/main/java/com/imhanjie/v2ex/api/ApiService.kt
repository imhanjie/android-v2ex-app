package com.imhanjie.v2ex.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/")
    suspend fun loadLatestTopics(
        @Query("tab") tab: String
    ): String

    @GET("/recent")
    suspend fun loadRecentTopics(
        @Query("p") pageIndex: Int
    ): String

    @GET("/go/{node_title}")
    suspend fun loadNodeTopics(
        @Path("node_title") nodeTitle: String,
        @Query("p") pageIndex: Int
    ): String

    @GET("/t/{topic_id}")
    suspend fun loadTopic(
        @Path("topic_id") id: Long
    ): String

}