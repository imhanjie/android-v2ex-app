package com.imhanjie.v2ex.repository

import com.imhanjie.v2ex.api.ApiServer
import com.imhanjie.v2ex.api.ApiService
import com.imhanjie.v2ex.model.Topic

object AppRepositoryImpl : AppRepository {

    private val api: ApiService = ApiServer.create()

    override suspend fun loadTopics(): List<Topic> {
        return api.loadTopics()
    }

}