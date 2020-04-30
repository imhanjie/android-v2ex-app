package com.imhanjie.v2ex.parser

interface Parserab {

    fun parserSignInProblem(html: String): String?

    /**
     * @param html https://v2ex.com/settings
     */
//    fun parserUserInfo(html: String): MyUserInfo

}