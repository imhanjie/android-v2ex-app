package com.imhanjie.v2ex.parser

import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testParser() {
//        val html = File("./html/v2ex_topic_subtle.html").readText()
//        val parser: Parser = TopicParser()
//        println(parser.parser(html))
        val content = "<div class=\"markdown_body\">\n" +
                "     <p>看到这么多人有这样的想法，我先说一下，希望大家理性对待，不要盲从。</p> \n" +
                "     <p>接下来我会开始我的计划，然后去执行，从计划到执行中遇到的任何问题，和想法，甚至是过程我都会在v站发帖跟进，你可以关注我这个v站号。</p> \n" +
                "     <p>同时我建立了一个群，但是先定下几个规矩， 1.不允许发除了自己产品以外的任何广告，包括我自己。 2.不允许卖课变相收割，包括我自己。 3.不允许发无谓的灌水，包括我自己。</p> \n" +
                "     <p>wechat: CodeGenesis</p> \n" +
                "    </div>"
        println(removeTailTag(content, "div"))
    }

    /**
     * 移除尾部元素标签
     */
    private fun removeTailTag(content: String, tagName: String): String {
        var result = content
        var tagStart = "<$tagName"
        val tagEnd = "</$tagName>"
        if (content.endsWith(tagEnd)) {
            val index = content.lastIndexOf(tagStart)
            tagStart = content.substring(index, content.indexOf(">", index) + 1)
            result = result.substring(0, index) + result.substring(index).replace(tagStart, "").replace(tagEnd, "")
        }
        return result
    }

}
