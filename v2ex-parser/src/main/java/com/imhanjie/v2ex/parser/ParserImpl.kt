package com.imhanjie.v2ex.parser

import com.imhanjie.v2ex.parser.model.Reply
import com.imhanjie.v2ex.parser.model.SignIn
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.parser.model.TopicItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.math.max

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

        val userAvatar = document.selectFirst("#Main").selectFirst("div.box").selectFirst("div.fr").selectFirst("img.avatar").attr("src")
        val userName: String
        val createTime: String
        val click: Long
        with(document.selectFirst("small.gray")) {
            userName = text().split(" · ")[0]
            createTime = text().split(" · ")[1]
            click = text().split(" · ")[2].split(" ")[0].toLong()
        }

        val content = (document.selectFirst("div.markdown_body")?.html() ?: document.selectFirst("div.topic_content")?.html()) ?: ""

        val currentPage = document.selectFirst("a.page_current")?.text()?.toInt() ?: 1
        var totalPage = currentPage
        with(document.select("a.page_normal")!!) {
            if (isNotEmpty()) {
                totalPage = max(last().text().toInt(), currentPage)
            }
        }

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
            replies,
            currentPage,
            totalPage
        )
    }

    private fun parserReplies(document: Document): List<Reply> {
        return document.select("#Main").select("div.cell").filter { eCell ->
            val attrId = eCell.attr("id")
            attrId.isNotEmpty() && attrId.startsWith("r_")
        }.map { eCell ->
            val replyId = eCell.attr("id").split("_")[1].toLong()
            val userAvatar = eCell.selectFirst("img.avatar").attr("src")
            val userName = eCell.selectFirst("a.dark").text()
            val content = eCell.selectFirst("div.reply_content").html()
            val time = eCell.selectFirst("span.ago").text()
            val likes = eCell.selectFirst("span.small.fade")?.text()?.toLong() ?: 0
            val no = eCell.selectFirst("span.no").text().toLong()
            Reply(
                replyId, userAvatar, userName, content, time, likes, no
            )
        }
    }

    override fun parserSignIn(html: String): SignIn {
        val document = Jsoup.parse(html)
        val eTrs = document.selectFirst("#Main").selectFirst("table").select("tr")
        val keyUserName = eTrs[0].selectFirst("input").attr("name")
        val keyPassword = eTrs[1].selectFirst("input").attr("name")
        val keyVerCode = eTrs[2].selectFirst("input").attr("name")
        val verificationUrl = "https://v2ex.com" + eTrs[2].selectFirst("div").attr("style").split(";")[0].split("'")[1]
        return SignIn(
            keyUserName,
            keyPassword,
            keyVerCode,
            verificationUrl
        )
    }

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