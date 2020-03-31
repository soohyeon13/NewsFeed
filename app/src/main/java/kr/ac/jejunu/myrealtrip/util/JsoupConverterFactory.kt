package kr.ac.jejunu.myrealtrip.util

import android.util.Log
import okhttp3.ResponseBody
import okhttp3.internal.cacheGet
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.net.URL
import java.nio.charset.Charset

object JsoupConverterFactory : Converter.Factory() {
    private val TAG = "JsoupConverter"

    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        return when (type) {
            Document::class.java -> JsoupConverter(retrofit!!.baseUrl().toString())
            else -> null
        }
    }

    private class JsoupConverter(val baseUri: String) : Converter<ResponseBody, Document?> {

        override fun convert(value: ResponseBody?): Document? {
            val parser = when (value?.contentType().toString()) {
                "application/xml", "text/xml" -> Parser.xmlParser()
                else -> Parser.htmlParser()
            }
            Log.d(TAG,"${value?.contentType()}")
            val chartName = value?.contentType()?.charset()?.name()
            if (chartName == "euc-kr") {
                return Jsoup.parse(value.byteStream(), "euc-kr", baseUri, parser)
            } else {
                return Jsoup.parse(value?.byteStream(), "utf-8", baseUri, parser)
            }
        }
    }
}