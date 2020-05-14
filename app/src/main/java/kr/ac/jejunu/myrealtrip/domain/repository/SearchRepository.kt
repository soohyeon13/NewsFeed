package kr.ac.jejunu.myrealtrip.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem

interface SearchRepository {
    fun loadSearchNews(news: String,page:Int) : Completable
    fun getSearchNews(): Observable<List<NewsItem>>
    fun clear()
}