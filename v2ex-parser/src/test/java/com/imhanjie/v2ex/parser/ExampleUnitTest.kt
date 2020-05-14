package com.imhanjie.v2ex.parser

import com.imhanjie.v2ex.parser.impl.NodeTopicsParser
import org.junit.Test
import java.io.File

class ExampleUnitTest {
    @Test
    fun testParser() {
        val html = File("./html/v2ex_node_topics.html").readText()
        val parser: Parser = NodeTopicsParser()
        println(parser.parser(html))
    }

}
