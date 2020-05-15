package com.imhanjie.v2ex.parser

import com.imhanjie.v2ex.parser.impl.NotificationsParser
import org.junit.Test
import java.io.File

class ExampleUnitTest {
    @Test
    fun testParser() {
        val html = File("./html/v2ex_notifications.html").readText()
        val parser: Parser = NotificationsParser()
        println(parser.parser(html))
    }

}
