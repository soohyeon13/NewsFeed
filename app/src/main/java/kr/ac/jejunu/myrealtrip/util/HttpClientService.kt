package kr.ac.jejunu.myrealtrip.util

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import java.lang.Exception
import java.lang.RuntimeException
import java.security.cert.CertificateEncodingException
import java.security.cert.X509Certificate
import javax.net.ssl.*

class HttpClientService {
    companion object {
        fun getUnsafeOkHttpClient(): OkHttpClient {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @Throws(CertificateEncodingException::class)
                    override fun checkClientTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    @Throws(CertificateEncodingException::class)
                    override fun checkServerTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                })

                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                val sslSocketFactory = sslContext.socketFactory
                val builder = OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                    .hostnameVerifier(HostnameVerifier { _, _ -> true })
                    .addInterceptor(ResponseInterceptor())
                    .build()
                return builder
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}