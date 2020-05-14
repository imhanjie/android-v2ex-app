package com.imhanjie.v2ex.parser

import com.imhanjie.v2ex.parser.impl.MyNodesParser
import org.junit.Test
import java.io.File

class ExampleUnitTest {
    @Test
    fun testParser() {
        val html = File("./html/v2ex_my_nodes.html").readText()
        val parser: Parser = MyNodesParser()
        println(parser.parser(html))
    }

}
