package com.imhanjie.v2ex.parser

import org.jsoup.Jsoup

object ParserImpl : Parserab {

    /**
     * 尝试解析 html 可能出现的登陆错误
     * @return 如果发现登录有错误则返回错误原因，若没有发现返回 null
     */
    override fun parserSignInProblem(html: String): String {
        val document = Jsoup.parse(html)
        val eProblem = document.selectFirst("div.problem")
        if (eProblem != null) {
            // 登录验证错误
            return eProblem.selectFirst("li").text()
        } else if (document.selectFirst("title").text().contains("登录受限")) {
            // 登录受限
            return document.selectFirst("div.topic_content.markdown_body").selectFirst("p").text()
        } else {
            return "未知错误"
        }
    }

}