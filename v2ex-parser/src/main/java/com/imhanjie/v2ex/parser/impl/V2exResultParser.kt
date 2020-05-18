package com.imhanjie.v2ex.parser.impl

import com.imhanjie.v2ex.parser.Parser
import com.imhanjie.v2ex.parser.model.V2exResult
import com.imhanjie.v2ex.parser.parseJson

class V2exResultParser : Parser {

    override fun parser(html: String): Any {
        return parseJson<V2exResult>(html)
    }

}