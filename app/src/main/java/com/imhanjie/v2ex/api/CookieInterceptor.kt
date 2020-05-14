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
     * 管理每个页面的 PB3_SESSION，当前页面的 once 与 PB3_SESSION 一一对应，
     * 所以某个页面上的操作接口中有 once 码，即请求时需要附带尚当前页面的 PB3_SESSION，
     * 可以通过请求头中的 Refer 地址来判断当前的操作和哪个页面相关。
     */
    private val pb3SessionMap = hashMapOf<String, String>()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = loadForRequest(request)
        val response = chain.proceed(request)
        saveFromResponse(request, response)
        return response
    }

    private fun saveFromResponse(request: Request, response: Response) {
        val responseUrl = response.request().url().toString()
        val pb3Session = response.headers().values("set-cookie").firstOrNull { it.startsWith("PB3_SESSION=") } ?: ""
        if (pb3Session != "") {
            if (responseUrl.startsWith("${ApiServer.BASE_URL}/go")) {
                pb3SessionMap["${ApiServer.BASE_URL}/go"] = pb3Session
            } else {
                pb3SessionMap[responseUrl] = pb3Session
            }
        }
    }

    private fun loadForRequest(request: Request): Request {
        val resultCookies = mutableListOf<Cookie>()

        val refererUrl = request.header("Referer").orEmpty()
        if (refererUrl.isNotEmpty()) {
            val requestUrl = request.url().toString()
            val pageSession: String = pb3SessionMap[refererUrl].orEmpty()
            if (pageSession.isNotEmpty()) {
                resultCookies.add(Cookie.parse(request.url(), pageSession)!!)
            }
        }

        // 接口请求时都去尝试取 A2 Cookie
        val a2Cookie = PreferencesManager.getInstance(SpConstants.FILE_COOKIES)
            .getString(SpConstants.COOKIE_A2, "")
        if (a2Cookie.isNotEmpty()) {
            resultCookies.add(Cookie.parse(request.url(), a2Cookie)!!)
        }

        return if (resultCookies.isEmpty()) {
            request
        } else {
            request.newBuilder().apply {
                header("Cookie", cookieHeader(resultCookies))
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