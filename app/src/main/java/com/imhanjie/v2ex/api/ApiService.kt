package com.imhanjie.v2ex.api

import com.imhanjie.v2ex.model.Topic
import retrofit2.http.GET

interface ApiService {

    @GET("topics/latest.json")
    suspend fun loadTopics(): List<Topic>


}