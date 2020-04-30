package com.imhanjie.v2ex.api

import com.imhanjie.support.PreferencesManager
import com.imhanjie.v2ex.common.SpConstants
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

object SignInCookieManager : CookieJar {

    /**
     * 临时保存用于登录的 cookies
     */
    private var memorySignInCookies = mutableListOf<Cookie>()

    override fun saveFromResponse(httpUrl: HttpUrl, cookies: MutableList<Cookie>) {
        val url = httpUrl.url().toString()
        if (!url.endsWith("/signin")) {
            return
        }
        memorySignInCookies.clear()
        memorySignInCookies.addAll(cookies)
    }

    override fun loadForRequest(httpUrl: HttpUrl): MutableList<Cookie> {
        val url = httpUrl.url().toString()
        return if (url.contains("once") || url.contains("signin")) {
            memorySignInCookies
        } else {
            // 除了上面两个接口请求时都去尝试取 A2 Cookie
            val a2Cookie = PreferencesManager.getInstance(SpConstants.FILE_COOKIES)
                .getString(SpConstants.COOKIE_A2, "")
            if (a2Cookie.isNotEmpty()) {
                // 已登录
                mutableListOf(Cookie.parse(httpUrl, a2Cookie)!!)
            } else {
                mutableListOf()
            }
        }
    }

}