package com.imhanjie.v2ex.parser.impl

import com.imhanjie.v2ex.parser.Parser
import com.imhanjie.v2ex.parser.model.TopicItem
import org.jsoup.Jsoup

class NodeTopicsParser : Parser {

    override fun parser(html: String): Any {
        val document = Jsoup.parse(html)
        return document.select("#TopicsNode").select("div.cell").map { eCell ->
            val eTitle = eCell.selectFirst("a.topic-link")
            val title = eTitle.text()
            val id = eTitle.attr("href").split("/")[2].split("#")[0].toLong()

            val eTopicInfo = eCell.selectFirst("span.topic_info")

            val userName = eTopicInfo.selectFirst("strong > a").text()

            val userAvatar = eCell.selectFirst("img.avatar").attr("src")
            val latestReplyTime = eTopicInfo.text().split(" • ")[1]

            val replies = eCell.selectFirst("a.count_livid")?.text()?.toLong() ?: 0

            TopicItem(
                id,
                title,
                "",
                "",
                userName,
                userAvatar,
                latestReplyTime,
                replies,
                isTop = false
            )
        }
    }

}