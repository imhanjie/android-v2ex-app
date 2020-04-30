package com.imhanjie.v2ex.parser

import com.imhanjie.v2ex.parser.model.SignIn

interface Parserab {

    fun parserSignIn(html: String): SignIn

    fun parserSignInProblem(html: String): String?

    /**
     * @param html https://v2ex.com/settings
     */
//    fun parserUserInfo(html: String): MyUserInfo

}