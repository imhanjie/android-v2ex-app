package com.imhanjie.v2ex.api

import com.imhanjie.support.e
import com.imhanjie.support.parseToJson
import com.imhanjie.v2ex.common.ParseException
import com.imhanjie.v2ex.model.Result
import com.imhanjie.v2ex.parser.impl.LatestTopicsParser
import com.imhanjie.v2ex.parser.impl.NodeTopicsParser
import com.imhanjie.v2ex.parser.Parser
import com.imhanjie.v2ex.parser.impl.TopicParser
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody

class V2exParserInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().toString()
        val response = chain.proceed(request)
        e("intercept: $url")
        val targetParser = getParser(url)
        if (targetParser == null) {
            // 未发现 html 解析器，说明不需要解析，直接返回 response
            return response
        } else {
            // 需要进行 html 解析
            val html = response.body()!!.string()
            val obj: Any
            try {
                obj = targetParser.parser(html)
            } catch (e: Exception) {
                throw ParseException()
            }
            val newResponseBody = ResponseBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                parseToJson(Result.success(obj))
            )
            return response.newBuilder().body(newResponseBody).build()
        }
    }

    /**
     * 根据 url 获取 html 解析器
     *
     * @param url 请求的 url
     * @return 若返回 null 说明不需要解析，反之需要解析
     */
    private fun getParser(url: String): Parser? {
        return with(url) {
            if (startsWith("https://v2ex.com/recent?p=")
                || startsWith("https://v2ex.com/?tab=")
            ) {
                LatestTopicsParser()
            } else if (startsWith("https://v2ex.com/t/")) {
                TopicParser()
            } else if (startsWith("https://v2ex.com/go/")) {
                NodeTopicsParser()
            } else {
                null
            }
        }
    }

}