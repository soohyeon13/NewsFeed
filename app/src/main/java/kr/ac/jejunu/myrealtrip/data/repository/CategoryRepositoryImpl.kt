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

class CategoryRepositoryImpl(private val service : NewsCategoryService) : CategoryRepository {
    companion object{
        private val TAG = "CategoryRepositoryImpl"
    }
    private val cateNewsItemsMap =
        BehaviorSubject.createDefault<LinkedHashMap<String?,NewsItem>>(linkedMapOf())

    override fun loadCateNews(country: String, category: String, key: String): Completable {
        return service.categoryNews(country,category,key)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                getNews(it)
            }
            .doOnError{error -> Log.d(TAG,error.message)}
            .ignoreElement()
    }

    private fun getNews(items: NewsCateResponse) {
        items.articles.forEach {
            Log.d("test" ,it.toString())
            val tempMap = cateNewsItemsMap.value!!
            if (!tempMap.containsKey(it.title)) {
                val keyword = findKeyWord(it.description)
                tempMap[it.title] =
                    NewsItem(it.title,it.urlToImage,it.description,it.description,it.url,keyword)
                cateNewsItemsMap.onNext(tempMap)
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

    override fun getCateNews(): Observable<List<NewsItem>> {
        return cateNewsItemsMap.map { it.values.toList() }.hide()
    }

    override fun clear() {
        cateNewsItemsMap.onNext(linkedMapOf())
    }
}