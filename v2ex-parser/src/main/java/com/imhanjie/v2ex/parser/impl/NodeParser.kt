package com.imhanjie.v2ex.parser.impl

import com.imhanjie.v2ex.parser.Parser
import com.imhanjie.v2ex.parser.model.TinyNode
import org.jsoup.Jsoup

class NodeParser : Parser {

    override fun parser(html: String): List<TinyNode> {
        val document = Jsoup.parse(html)
        val nodeList = mutableListOf<TinyNode>()
        for (eInner in document.selectFirst("#Main").select("div.inner")) {
            for (item in eInner.select("a")) {
                val name = item.attr("href").split("/")[2]
                val title = item.text()
                nodeList.add(TinyNode(title, name))
            }
        }
        return nodeList
    }

}