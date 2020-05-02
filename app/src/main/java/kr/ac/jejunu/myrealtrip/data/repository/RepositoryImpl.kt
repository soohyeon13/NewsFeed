package kr.ac.jejunu.myrealtrip.data.repository

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kr.ac.jejunu.myrealtrip.data.response.RssResponse
import kr.ac.jejunu.myrealtrip.data.service.HtmlService
import kr.ac.jejunu.myrealtrip.data.service.RssService
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.domain.repository.Repository

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

    private val newsItemsMap =
        BehaviorSubject.createDefault<LinkedHashMap<String?, NewsItem>>(linkedMapOf())

    override fun loadNews(): Completable {
        return rssService.searchRss()
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                if (!it.channelResponse?.itemResponse.isNullOrEmpty())
                    loadHtml(it)
            }.ignoreElement()
    }

    private fun loadHtml(rssResponse: RssResponse) {
        rssResponse.channelResponse?.itemResponse?.forEach { item ->
            val url = item.link.toString()
            val regex = Regex(pattern = "(?<=(/))\\w*(?=\\?)").find(url)
            val resultReg: String? = regex?.value
            val title = item.title
            getHtml(resultReg, title, url)
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun getHtml(resultRex: String?, title: String?, url: String) {
        resultRex?.let { reg ->
            htmlService
                .getHtml(reg, URL_QUERY)
                .subscribeOn(Schedulers.io())
                .subscribe({ document ->
                    val imgUrl = document.head().select(META_IMAGE_TAG).attr(META_CONTENT)
                    val des = document.head().select(META_DESCRIPTION_TAG).attr(META_CONTENT)
                    val content = document.body().select(BODY_ARTICLE_CONTENT).text()
                    var keyContent = content
                    if (content.isNullOrEmpty()) keyContent = des
                    val tempMap = newsItemsMap.value!!
                    if (!tempMap.contains(title) && !des.contains("\uFFFD")) {
                        val keywords = findKeyWord(keyContent)
                        tempMap[title] =
                            NewsItem(title, imgUrl, des, content, url, keywords)
                        Log.d(TAG,"${tempMap}")
                        newsItemsMap.onNext(tempMap)
                    }
                }, {
                    Log.d(TAG, "error ${it.message}")
                })
        }
    }

    private fun findKeyWord(content: String?): List<String> {
        val wordMap = mutableMapOf<String, Int?>()
        val regex = Regex(pattern = "([^0-9a-zA-Z가-힣])")
        content?.split(" ")
            ?.filter { it.length > 2 }
            ?.forEach { word ->
                val removeDot = word.replace(regex, "")
                if (!wordMap.containsKey(word)) wordMap[removeDot] = 1
                else wordMap[removeDot] = wordMap[removeDot]?.plus(1)
            }

        val sortWordMapToList =
            wordMap.toList()
                .sortedByDescending { (_, value) -> value }
                .groupBy { (_, value) -> value }
                .toList()
        var maxKeyWord = 3
        val keyWords = mutableListOf<String>()
        loop@ for (i in sortWordMapToList.indices) {
            val sortKeyWord = sortByWord(sortWordMapToList, i)
            if (sortWordMapToList[i].second.size < maxKeyWord) {
                for (j in sortKeyWord.indices) {
                    if (keyWords.size == 3) break@loop
                    keyWords.add(sortKeyWord[j].first)
                }
                maxKeyWord -= sortWordMapToList[i].second.size
            } else {
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

    override fun getNewsItems(page: Int): Observable<List<NewsItem>> {
        return newsItemsMap.filter { it.values.size < page * 20 }
            .map { it.values.toList().takeLast(page * 20) }.distinctUntilChanged().hide()
    }
}
