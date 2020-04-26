package com.imhanjie.v2ex.parser

import com.imhanjie.v2ex.parser.model.Reply
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.parser.model.TopicItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object ParserImpl : Parser {

    override fun parseLatestTopics(html: String): List<TopicItem> {
        val document = Jsoup.parse(html)
        return document.select("div.cell.item").map { eCell ->
            val eTitle = eCell.selectFirst("a.topic-link")
            val isTop = eCell.attr("style").isNotEmpty()
            val title = eTitle.text()
            val id = eTitle.attr("href").split("/")[2].split("#")[0].toLong()

            val eTopicInfo = eCell.selectFirst("span.topic_info")

            val eNode = eTopicInfo.selectFirst("a.node")
            val nodeTitle = eNode.text()
            val nodeName = eNode.attr("href").split("/")[2]

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
                replies,
                isTop
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
                replies,
                isTop = false
            )
        }
    }

    override fun parserTopic(html: String): Topic {
        val document = Jsoup.parse(html)

        val id = document.selectFirst("div.votes").attr("id").split("_")[1].toLong()
        val title = document.selectFirst("div.header").selectFirst("h1").text()

        var nodeName = ""
        var nodeTitle = ""
        for (ae in document.selectFirst("div.header").select("a")) {
            val href = ae.attr("href")
            val key = "/go/"
            if (href.isNotEmpty() && href.startsWith(key)) {
                nodeName = href.split(key)[1]
                nodeTitle = ae.text()
                break
            }
        }

        val userAvatar = document.selectFirst("div.fr").selectFirst("img.avatar").attr("src")
        val userName: String
        val createTime: String
        val click: Long
        with(document.selectFirst("small.gray")) {
            userName = text().split(" · ")[0]
            createTime = text().split(" · ")[1]
            click = text().split(" · ")[2].split(" ")[0].toLong()
        }

        val content = document.selectFirst("div.topic_content").html()

        val replies: List<Reply> = parserReplies(document)
        return Topic(
            id,
            title,
            nodeName,
            nodeTitle,
            userName,
            userAvatar,
            createTime,
            click,
            content,
            replies
        )
    }

    private fun parserReplies(document: Document): List<Reply> {
        return document.select("#Main").select("div.cell").filter { eCell ->
            val attrId = eCell.attr("id")
            attrId.isNotEmpty() && attrId.startsWith("r_")
        }.map { eCell ->
            val replyId = eCell.attr("id").split("_")[1].toLong()
            val userAvatar = eCell.select("img.avatar").attr("src")
            val userName = eCell.select("a.dark").text()
            val content = eCell.select("div.reply_content").html()
            val time = eCell.select("span.ago").text()
            val likes = eCell.selectFirst("span.small.fade")?.text()?.toLong() ?: 0
            val no = eCell.select("span.no").text().toLong()
            Reply(
                replyId, userAvatar, userName, content, time, likes, no
            )
        }
    }

}