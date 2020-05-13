package kr.ac.jejunu.myrealtrip.util

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import kr.ac.jejunu.myrealtrip.BuildConfig
import java.util.concurrent.TimeUnit

class SearchClient {
    companion object{
        private val clientId :String = BuildConfig.Client_ID
        private val clientSecret = BuildConfig.Client_Secret
        fun httpClient() : OkHttpClient {
            val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            val clientBuilder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                clientBuilder.addInterceptor(httpLoggingInterceptor)
            }
            clientBuilder.addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("X-Naver-Client-Id",clientId)
                    .addHeader("X-Naver-Client-Secret",clientSecret)
                    .build()
                chain.proceed(newRequest)
            }
            clientBuilder.readTimeout(120,TimeUnit.SECONDS)
            clientBuilder.writeTimeout(120,TimeUnit.SECONDS)
            clientBuilder.connectTimeout(120,TimeUnit.SECONDS)
            return clientBuilder.build()
        }
    }
}