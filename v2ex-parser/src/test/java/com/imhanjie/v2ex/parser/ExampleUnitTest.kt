package com.imhanjie.v2ex.parser

import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testParser() {
//        val html = File("./html/v2ex_topic_subtle.html").readText()
//        val parser: Parser = TopicParser()
//        println(parser.parser(html))
        val s = "asdasdasdasdndjkasnjd<p>asdasd</p><p><strong>希望这个帖子能给和我一样有蓝牙问题的老哥们带来点帮助。</strong></p>"
        if (s.endsWith("</p>")) {
            val index = s.lastIndexOf("<p>")
            println(s.substring(0, index) + s.substring(index).replace("<p>", "").replace("</p>", ""))
        }
    }

}
