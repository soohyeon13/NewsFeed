package kr.ac.jejunu.myrealtrip.util

import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return response.newBuilder()
            .addHeader("Content-Type","text/html; charset=utf-8")
            .build()
    }
}