package kr.ac.jejunu.myrealtrip

import android.util.Log
import kr.ac.jejunu.myrealtrip.data.repository.RepositoryImpl
import kr.ac.jejunu.myrealtrip.data.service.HtmlService
import kr.ac.jejunu.myrealtrip.domain.repository.Repository
import kr.ac.jejunu.myrealtrip.util.JsoupConverterFactory
import org.jsoup.Jsoup
import org.junit.Test

import org.junit.Assert.*
import java.net.URL
import java.nio.charset.Charset

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Jsoup.connect("https://news.google.com/__i/rss/rd/articles/CBMiNmh0dHBzOi8vd3d3Lm1rLmNvLmtyL25ld3Mvc29jaWV0eS92aWV3LzIwMjAvMDMvMzI2MjI5L9IBOGh0dHBzOi8vbS5tay5jby5rci9uZXdzL3NvY2lldHkvdmlldy1hbXAvMjAyMC8wMy8zMjYyMjkv?oc=5")
            .get().run {
                println(this)
                this.body().select("div[itemprop=articleBody]").forEach {
                    println(it.text())
                }
                select("meta[property=og:image]").forEachIndexed { index, element ->
                    val imageUrl =element.attr("content")
                    println("url : $imageUrl")
                }
                select("meta[property=og:description]").forEachIndexed { index, element ->
                    val text = element.attr("content")
                    println("text: $text")
                }
            }
    }
}

