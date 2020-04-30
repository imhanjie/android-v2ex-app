package com.imhanjie.v2ex.parser

interface Parser {

    fun parser(html: String): Any

}