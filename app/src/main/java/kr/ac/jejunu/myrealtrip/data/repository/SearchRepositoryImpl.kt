package kr.ac.jejunu.myrealtrip.data.repository

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kr.ac.jejunu.myrealtrip.data.response.RssSearchResponse
import kr.ac.jejunu.myrealtrip.data.service.SearchService
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.domain.repository.SearchRepository
import org.jsoup.Jsoup

class SearchRepositoryImpl(
    private val searchService: SearchService
) : SearchRepository {
    companion object {
        private val META_IMAGE_TAG = "meta[property=og:image]"
        private val META_CONTENT = "content"
        private val BODY_ARTICLE_CONTENT = "div[itemprop=articleBody]"
        private val TAG = "SearchRepositoryImpl"
    }

    private val searchItemsMap =
        BehaviorSubject.createDefault<LinkedHashMap<String, NewsItem>>(linkedMapOf())

    override fun loadSearchNews(news: String, page: Int): Completable {
        val p = page * 20
        return searchService
            .getSearchNews(news, display = p)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                if (!it.items.isNullOrEmpty()) loadResultUrl(it)
            }
            .doOnError { error -> Log.d(TAG,"error ${error.message}") }
            .ignoreElement()
    }

    private fun loadResultUrl(searchResult: RssSearchResponse) {
        searchResult.items.forEach {
            val doc = Jsoup.connect(it.originallink).get()
            val img = doc.head().select(META_IMAGE_TAG).attr(META_CONTENT)
            val title = it.title
                .replace("<b>","")
                .replace("</b>","")
                .replace("&quot;","")
            val content = doc.body().select(BODY_ARTICLE_CONTENT).text()
            var keyword = content
            if (keyword.isNullOrEmpty()) keyword = it.description
            val tempMap = searchItemsMap.value!!
            println(title)
            if (!tempMap.containsKey(title)) {
                val keywords = findKeyWord(keyword)
                tempMap[title] =
                    NewsItem(title, img, it.description, content, it.originallink, keywords)
                Log.d(TAG,tempMap.toString())
                searchItemsMap.onNext(tempMap)
            }
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

    override fun getSearchNews(): Observable<List<NewsItem>> {
        return searchItemsMap.map { it.values.toList() }.hide()
    }
}