package com.imhanjie.v2ex.common

import java.util.regex.Pattern

object RegexPattern {
    val TOPIC_URL_PATTERN: Pattern = Pattern.compile("^((http|https)://)?(www.)?v2ex.com/t/\\d+(#.*)?\$")
    val IMAGE_URL_PATTERN: Pattern = Pattern.compile("^.*\\.(png|jpg|jpeg|gif)$")
}