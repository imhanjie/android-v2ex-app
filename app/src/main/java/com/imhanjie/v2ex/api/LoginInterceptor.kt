package com.imhanjie.v2ex.api

import android.annotation.SuppressLint
import com.imhanjie.support.e
import com.imhanjie.v2ex.model.LoginInfo
import com.imhanjie.v2ex.parser.impl.SignInProblemParser
import okhttp3.Interceptor
import okhttp3.Response

class LoginInterceptor : Interceptor {

    @SuppressLint("DefaultLocale")
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().toString()
        val method = request.method()
        val response = chain.proceed(request)
        if (url != "${ApiServer.BASE_URL}/signin" || method.toUpperCase() != "POST") {
            e("不需要登录处理")
            return response
        }
        e("需要登录处理")
        // 根据响应头中是否有 "A2" cookie 来判定是否登录成功
        val a2Cookie: String? = response.headers().values("set-cookie").firstOrNull { it.startsWith("A2=") }
        return if (a2Cookie != null) {
            // 登录成功
            response.recreateSuccessJsonResponse(LoginInfo(a2Cookie))
        } else {
            // 登陆失败
            try {
                val html = response.body()!!.string()
                response.recreateFailJsonResponse(SignInProblemParser().parser(html).toString())
            } catch (e: Exception) {
                response.recreateFailJsonResponse("登录问题数据解析错误")
            }

        }
    }

}