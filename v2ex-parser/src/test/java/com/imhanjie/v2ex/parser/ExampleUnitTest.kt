package com.imhanjie.v2ex.parser

import com.imhanjie.v2ex.parser.impl.SettingsParser
import org.junit.Test
import java.io.File

class ExampleUnitTest {
    @Test
    fun testParser() {
        val html = File("./html/v2ex_settings.html").readText()
        val parser: Parser = SettingsParser()
        println(parser.parser(html))
    }

}
