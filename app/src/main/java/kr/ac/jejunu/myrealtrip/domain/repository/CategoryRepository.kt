package kr.ac.jejunu.myrealtrip.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem

interface CategoryRepository {
    fun loadCateNews(country : String,category: String, key : String) : Completable
    fun getCateNews() : Observable<List<NewsItem>>
    fun clear()
}