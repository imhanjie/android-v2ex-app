package com.imhanjie.v2ex.parser

import java.util.regex.Pattern

object RegexPattern {
    val TOPIC_URL_PATTERN: Pattern = Pattern.compile("^(((http|https)://)?(www.)?v2ex.com)?/t/\\d+((#.*)|(\\?p=\\d*))?\$")
    val IMAGE_URL_PATTERN: Pattern = Pattern.compile("^.*\\.(png|jpg|jpeg|gif)$")
    val PAGE_ONCE_PATTERN: Pattern = Pattern.compile("once=\\d+")
}