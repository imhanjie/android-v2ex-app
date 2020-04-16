package com.imhanjie.v2ex.parser

import com.imhanjie.v2ex.parser.model.TopicItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

object ParserImpl : Parser {

    override fun parseLatestTopics(html: String): List<TopicItem> {
        val document = Jsoup.parse(html)
        return document.select("div.cell.item").map { eCell ->
            val eTitle = eCell.selectFirst("a.topic-link")
            val title = eTitle.text()
            val id = eTitle.attr("href").split("/")[2].split("#")[0].toLong()

            val eTopicInfo = eCell.selectFirst("span.topic_info")

            val eNode = eTopicInfo.selectFirst("a.node")
            val nodeName = eNode.text()
            val nodeTitle = eNode.attr("href").split("/")[2]

            val userName = eTopicInfo.selectFirst("strong > a").text()

            val userAvatar = eCell.selectFirst("img.avatar").attr("src")
            val latestReplyTime = eTopicInfo.text().split(" • ")[2]

            val replies = eCell.selectFirst("a.count_livid")?.text()?.toLong() ?: 0

            TopicItem(
                id,
                title,
                nodeName,
                nodeTitle,
                userName,
                userAvatar,
                latestReplyTime,
                replies
            )
        }
    }

    override fun parseNodeTopics(
        html: String,
        nodeName: String,
        nodeTitle: String
    ): List<TopicItem> {
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
                nodeName,
                nodeTitle,
                userName,
                userAvatar,
                latestReplyTime,
                replies
            )
        }
    }

    private fun parseTopicCell(eCell: Element): TopicItem {
        val eTitle = eCell.selectFirst("a.topic-link")
        val title = eTitle.text()
        val id = eTitle.attr("href").split("/")[2].split("#")[0].toLong()

        val eTopicInfo = eCell.selectFirst("span.topic_info")

        val eNode = eTopicInfo.selectFirst("a.node")
        val nodeName = eNode.text()
        val nodeTitle = eNode.attr("href").split("/")[2]

        val userName = eTopicInfo.selectFirst("strong > a").text()

        val userAvatar = eCell.selectFirst("img.avatar").attr("src")
        val latestReplyTime = eTopicInfo.text().split(" • ")[2]

        val replies = eCell.selectFirst("a.count_livid")?.text()?.toLong() ?: 0

        return TopicItem(
            id,
            title,
            nodeName,
            nodeTitle,
            userName,
            userAvatar,
            latestReplyTime,
            replies
        )
    }

}