package com.imhanjie.v2ex.parser

import com.imhanjie.v2ex.parser.impl.NavNodeParser
import org.junit.Test
import java.io.File

class ExampleUnitTest {
    @Test
    fun testParser() {
        val html = File("./html/v2ex_nav_node.html").readText()
        val parser: Parser = NavNodeParser()
        println(parser.parser(html))
    }

}
