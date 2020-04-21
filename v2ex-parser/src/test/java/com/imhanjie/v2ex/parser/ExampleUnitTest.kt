package com.imhanjie.v2ex.parser

import org.junit.Test
import java.io.File

class ExampleUnitTest {
    @Test
    fun testParser() {
        val html = File("./html/v2ex_topic.html").readText()
        val parser: Parser = ParserImpl
        for (reply in parser.parserReplies(html)) {
            println(reply)
        }
    }

}
