package com.imhanjie.v2ex.parser

import org.junit.Test
import java.io.File

class ExampleUnitTest {
    @Test
    fun testParser() {
        val html = File("./html/v2ex_node.html").readText()
        val parser: Parser = ParserImpl
        for (topic in parser.parseNodeTopics(html, "node_name", "node_title")) {
            println(topic)
        }
    }

}
