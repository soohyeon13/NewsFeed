package kr.ac.jejunu.myrealtrip

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
        Jsoup.connect("https://nypost.com/2020/03/18/voters-brave-coronavirus-make-joe-biden-presumptive-nominee/")
            .get().run {
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
