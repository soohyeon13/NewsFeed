package kr.ac.jejunu.myrealtrip

import android.util.Log
import org.jsoup.Jsoup
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Jsoup.connect("https://news.google.com/__i/rss/rd/articles/CBMiKGh0dHBzOi8vd3d3Lm5vY3V0bmV3cy5jby5rci9uZXdzLzUzMTI4NTTSASpodHRwczovL20ubm9jdXRuZXdzLmNvLmtyL25ld3MvYW1wLzUzMTI4NTQ?oc=5")
            .get().run {
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
