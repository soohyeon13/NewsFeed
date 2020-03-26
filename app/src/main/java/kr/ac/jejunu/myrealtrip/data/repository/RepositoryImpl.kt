package kr.ac.jejunu.myrealtrip.data.repository

import android.util.Log
import androidx.core.text.parseAsHtml
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kr.ac.jejunu.myrealtrip.data.response.RssResponse
import kr.ac.jejunu.myrealtrip.data.service.HtmlService
import kr.ac.jejunu.myrealtrip.data.service.RssService
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.domain.repository.Repository
import org.jsoup.Jsoup
import java.net.URLEncoder
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList

class RepositoryImpl(
    private val rssService: RssService,
    private val htmlService: HtmlService
) : Repository {
    companion object {
        private val META_IMAGE_TAG = "meta[property=og:image]"
        private val META_DESCRIPTION_TAG = "meta[property=og:description]"
        private val META_CONTENT = "content"
        private val BODY_ARTICLE_CONTENT = "div[itemprop=articleBody]"
        private val URL_QUERY = "oc=5"
        private val TAG = "RepositoryImpl"
    }

    private val rssSubject = BehaviorSubject.create<RssResponse>()
    private val htmlSubject = BehaviorSubject.create<Map<String?, NewsItem>>()
    private var newsItemsSubject = BehaviorSubject.create<MutableList<Optional<NewsItem>>>()
    override fun loadRss(): Completable {
        return rssService.searchRss()
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                if (!it.channelResponse?.itemResponse.isNullOrEmpty()) {
                    rssSubject.onNext(it)
                    loadNewsItems(it)
                }
            }.ignoreElement()
    }

    private fun loadNewsItems(rssResponse: RssResponse) {
        rssResponse.channelResponse?.itemResponse?.also {
            newsItemsSubject.onNext(MutableList(it.size){Optional.empty<NewsItem>()})
        }?.forEachIndexed { index, itemResponse ->
            val url = itemResponse.link.toString()
            val regex = Regex(pattern = "(?<=(/))\\w*(?=\\?)").find(url)
            val resultReg: String? = regex?.value
            resultReg?.let { reg ->
                htmlService.getHtml(reg, URL_QUERY)
                    .subscribeOn(Schedulers.io())
                    .subscribe({ document ->
                        document.outputSettings().charset("UTF-8").outline(true).prettyPrint(true)
                        Log.d(TAG,"${document.charset()}")
                        val imgUrl = document.head().select(META_IMAGE_TAG).attr(META_CONTENT)
                        val des = document.head().select(META_DESCRIPTION_TAG).attr(META_CONTENT)
                        val content = document.body().select(BODY_ARTICLE_CONTENT).text()
                        Log.d(TAG,"charset : ${document.outputSettings().charset().newEncoder().charset()}title : ${itemResponse.title} des : $des content : $content")
                        newsItemsSubject.value!!.apply {
                            set(
                                index,
                                Optional.of(NewsItem(
                                    title = itemResponse.title,
                                    imageUrl = imgUrl,
                                    desc = des,
                                    content = content,
                                    link = itemResponse.link,
                                    keyWord = findKeyWord(content ?: des)
                                ))
                            )
                        }.let {items ->
                            newsItemsSubject.onNext(items)
                        }
                    }, {
                        Log.d(TAG, "error: ${it.message}")
                    })
            }
        }
    }

    private fun findKeyWord(content: String?) :List<String> {
        val wordMap = mutableMapOf<String, Int?>()
        val regex = Regex(pattern = "([^0-9a-zA-Z가-힣])")
        content?.split(" ")
            ?.filter { it.length > 2 }
            ?.forEach { word ->
                val removeDot = word.replace(regex, "")
                if (!wordMap.containsKey(word)) wordMap[removeDot] = 1
                else wordMap[removeDot] = wordMap[removeDot]?.plus(1)
            }

        val sortMapToList =
            wordMap.toList()
                .sortedByDescending { (_, value) -> value }
                .groupBy { (_, value) -> value }
                .toList()
        var maxKeyWord = 3
        val keyWords = mutableListOf<String>()
        loop@ for (i in sortMapToList.indices) {
            if (sortMapToList[i].second.size < maxKeyWord) {
                val sortKeyWord = sortByWord(sortMapToList, i)
                for (j in sortKeyWord.indices) {
                    if (keyWords.size == 3) break@loop
                    keyWords.add(sortKeyWord[j].first)
                }
                maxKeyWord -= sortMapToList[i].second.size
            } else {
                val sortKeyWord = sortByWord(sortMapToList, i)
                for (j in 0 until maxKeyWord) {
                    keyWords.add(sortKeyWord[j].first)
                }
                break@loop
            }
        }
       return keyWords
    }

    private fun sortByWord(words: List<Pair<Int?, List<Pair<String, Int?>>>>, index: Int)
            : List<Pair<String, Int?>> = words[index].second.sortedBy { (word, _) -> word }


    override fun getRss(): Observable<RssResponse> {
        return rssSubject.hide()
    }

    override fun getHtml(): Observable<Map<String?, NewsItem>> {
        return htmlSubject.hide()
    }

    override fun getNews(title: String): Observable<NewsItem> {
        return htmlSubject.map {
            it[title]
        }
    }

    override fun getNewsItems():Observable<List<Optional<NewsItem>>> {
        return newsItemsSubject.map {
            listOf(*it.toTypedArray())
        }.hide()
    }
}
