package kr.ac.jejunu.myrealtrip.data.repository

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kr.ac.jejunu.myrealtrip.data.response.NewsCateResponse
import kr.ac.jejunu.myrealtrip.data.service.NewsCategoryService
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.domain.repository.CategoryRepository
import kr.ac.jejunu.myrealtrip.util.Cate

class CategoryRepositoryImpl(private val service: NewsCategoryService) : CategoryRepository {
    companion object {
        private val TAG = "CategoryRepositoryImpl"
    }

    private val businessNewsItemsMap =
        BehaviorSubject.createDefault<LinkedHashMap<String?, NewsItem>>(linkedMapOf())
    private val entertainItemsMap =
        BehaviorSubject.createDefault<LinkedHashMap<String?, NewsItem>>(linkedMapOf())
    private val generalItemsMap =
        BehaviorSubject.createDefault<LinkedHashMap<String?, NewsItem>>(linkedMapOf())
    private val healthItemsMap =
        BehaviorSubject.createDefault<LinkedHashMap<String?, NewsItem>>(linkedMapOf())
    private val scienceItemsMap =
        BehaviorSubject.createDefault<LinkedHashMap<String?, NewsItem>>(linkedMapOf())
    private val sportsItemsMap =
        BehaviorSubject.createDefault<LinkedHashMap<String?, NewsItem>>(linkedMapOf())
    private val technologyItemsMap =
        BehaviorSubject.createDefault<LinkedHashMap<String?, NewsItem>>(linkedMapOf())

    override fun loadCateNews(country: String, category: String, key: String): Completable {
        return service.categoryNews(country, category, key)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                getNews(it, category)
            }
            .doOnError { error -> Log.d(TAG, error.message) }
            .ignoreElement()
    }

    private fun getNews(items: NewsCateResponse, cate: String) {
        items.articles.forEach {
            Log.d("test", it.toString())
            val tempMap = subjectDistinct(cate).value!!
            if (!tempMap.containsKey(it.title)) {
                val keyword = findKeyWord(it.description)
                tempMap[it.title] =
                    NewsItem(it.title, it.urlToImage, it.description, it.description, it.url, keyword)
                subjectDistinct(cate).onNext(tempMap)
            }
        }
    }

    private fun subjectDistinct(category : String): BehaviorSubject<LinkedHashMap<String?, NewsItem>> {
        return when (category) {
            Cate.BUSINESS.query -> businessNewsItemsMap
            Cate.ENTERTAINMENT.query -> entertainItemsMap
            Cate.GENERAL.query -> generalItemsMap
            Cate.HEALTH.query -> healthItemsMap
            Cate.SCIENCE.query -> scienceItemsMap
            Cate.SPORTS.query -> sportsItemsMap
            else -> technologyItemsMap
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

    override fun getCateNews(category: String): Observable<List<NewsItem>> {
        return subjectDistinct(category).map { it.values.toList() }.hide()
    }

    override fun clear() {
        businessNewsItemsMap.onNext(linkedMapOf())
    }
}