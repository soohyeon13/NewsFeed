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
            val charset: Charset
            if (value?.contentType()?.charset().toString().toLowerCase() == "euc-kr") {
                charset = value?.contentType()?.charset() ?: Charset.forName("euc-kr")
            } else {
                charset = value?.contentType()?.charset() ?: Charset.forName("utf-8")
            }
            val parser = when (value?.contentType().toString().toLowerCase()) {
                "application/html", "text/html; charset=euc-kr","text/html","text/html; charset=utf-8" -> Parser.htmlParser()
                else -> Parser.xmlParser()
            }
            return Jsoup.parse(value?.byteStream(), charset.name(), baseUri, parser)
        }
    }
}