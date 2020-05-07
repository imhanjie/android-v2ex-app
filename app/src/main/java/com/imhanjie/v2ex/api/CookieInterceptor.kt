package com.imhanjie.v2ex.api

import android.annotation.SuppressLint
import com.imhanjie.support.PreferencesManager
import com.imhanjie.v2ex.common.SpConstants
import okhttp3.Cookie
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

@SuppressLint("DefaultLocale")
class CookieInterceptor : Interceptor {

    /**
     * 临时保存用于登录的 cookies
     */
    private var memorySignInCookies = mutableListOf<Cookie>()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = loadForRequest(request)
        val response = chain.proceed(request)
        saveFromResponse(request, response)
        return response
    }


    private fun saveFromResponse(request: Request, response: Response) {
        if (!request.url().toString().startsWith("${ApiServer.BASE_URL}/signin") || request.method()
                .toUpperCase() != "GET" || response.code() == 302
        ) {
            return
        }
        val cookies = Cookie.parseAll(request.url(), response.headers())
        memorySignInCookies.clear()
        memorySignInCookies.addAll(cookies)
    }

    private fun loadForRequest(request: Request): Request {
        val url = request.url().toString()
        val method = request.method().toUpperCase()
        val cookies =
            if (url.startsWith("${ApiServer.BASE_URL}/_captcha?once=") || (url.startsWith("${ApiServer.BASE_URL}/signin") && method == "POST")) {
                memorySignInCookies
            } else {
                // 除了上面两个接口请求时都去尝试取 A2 Cookie
                val a2Cookie = PreferencesManager.getInstance(SpConstants.FILE_COOKIES)
                    .getString(SpConstants.COOKIE_A2, "")
                if (a2Cookie.isNotEmpty()) {
                    // 已登录
                    mutableListOf(Cookie.parse(request.url(), a2Cookie)!!)
                } else {
                    mutableListOf()
                }
            }
        return if (cookies.isEmpty()) {
            request
        } else {
            request.newBuilder().apply {
                header("Cookie", cookieHeader(cookies))
            }.build()
        }
    }

    /** Returns a 'Cookie' HTTP request header with all cookies, like `a=b; c=d`.  */
    private fun cookieHeader(cookies: List<Cookie>): String {
        val cookieHeader = StringBuilder()
        var i = 0
        val size = cookies.size
        while (i < size) {
            if (i > 0) {
                cookieHeader.append("; ")
            }
            val cookie = cookies[i]
            cookieHeader.append(cookie.name()).append('=').append(cookie.value())
            i++
        }
        return cookieHeader.toString()
    }

}