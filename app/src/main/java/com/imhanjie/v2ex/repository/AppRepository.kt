package com.imhanjie.v2ex.repository

import com.imhanjie.v2ex.model.Topic

interface AppRepository {

    suspend fun loadTopics(): List<Topic>

}